package com.ruoyi.common.aspectj;

import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.entity.SysRole;
import com.ruoyi.common.entity.SysUser;
import com.ruoyi.common.model.login.LoginUser;
import com.ruoyi.common.util.StringUtils;
import com.ruoyi.common.util.SecurityUtils;
import com.ruoyi.common.security.context.PermissionContextHolder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Aspect
@Slf4j
@Component
public class DataScopePermissionHandler implements DataPermissionHandler {

    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 通过 ThreadLocal 记录权限相关的属性值
     */
    private static final ThreadLocal<DataScope> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 清空当前线程保存的权限信息
     */
    @After("@annotation(controllerDataScope)")
    public void clearThreadLocal(DataScope controllerDataScope) {
        THREAD_LOCAL.remove();
    }

    @Before("@annotation(controllerDataScope)")
    public void doBefore(DataScope controllerDataScope) {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null) {
            return;
        }
        SysUser currentUser = loginUser.getUser();
        if (currentUser == null || currentUser.isAdmin()) {
            return;
        }
        THREAD_LOCAL.set(controllerDataScope);
    }

    /**
     * @param where 原SQL Where 条件表达式
     * @param mappedStatementId Mapper接口方法ID
     */
    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        val controllerDataScope = THREAD_LOCAL.get();
        if (controllerDataScope == null) {
            return where;
        }
        String permission = StringUtils.defaultIfEmpty(controllerDataScope.permission(), PermissionContextHolder.getContext());
        String deptAlias = controllerDataScope.deptAlias();
        String userAlias = controllerDataScope.userAlias();

        if (where == null) {
            where = new HexValue(" TRUE ");
        }
        StringBuilder sqlString = new StringBuilder();
        Set<String> conditions = new HashSet<>();

        SysUser user = SecurityUtils.getLoginUser().getUser();
        for (SysRole role : user.getRoles()) {
            String dataScope = role.getDataScope();
            if (!DATA_SCOPE_CUSTOM.equals(dataScope) && conditions.contains(dataScope)) {
                continue;
            }
            if (StringUtils.isNotEmpty(permission)
                    && StringUtils.isNotEmpty(role.getPermissions())
                    && !StringUtils.containsAny(role.getPermissions(), permission.split(","))) {
                continue;
            }
            if (DATA_SCOPE_ALL.equals(dataScope)) {
                sqlString.setLength(0);
                conditions.add(dataScope);
                break;
            } else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
                sqlString.append(" OR ")
                        .append(deptAlias).append(".dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = ")
                        .append(role.getRoleId()).append(" ) ");
            } else if (DATA_SCOPE_DEPT.equals(dataScope)) {
                sqlString.append(" OR ").append(deptAlias).append(".dept_id = ").append(user.getDeptId());
            } else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
                sqlString.append(" OR ")
                        .append(deptAlias).append(".dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = ")
                        .append(user.getDeptId()).append(" or find_in_set( ").append(user.getDeptId());
            } else if (DATA_SCOPE_SELF.equals(dataScope)) {
                if (userAlias.isBlank()) {
                    // 数据权限为仅本人且没有userAlias别名不查询任何数据
                    sqlString.append(" OR ").append(deptAlias).append(".dept_id = 0");
                } else {
                    sqlString.append(" OR ").append(userAlias).append(".user_id = ").append(user.getUserId());
                }
            }
            conditions.add(dataScope);
        }
        // 多角色情况下，所有角色都不包含传递过来的权限字符，这个时候sqlString也会为空，所以要限制一下,不查询任何数据
        if (conditions.isEmpty()) {
            sqlString.append(deptAlias).append(".dept_id = 0");
        }
        if (sqlString.length() == 0) {
            return where;
        }
        sqlString.replace(0, 4, "(").append(")");
        Expression between = new HexValue(sqlString.toString());

        return new AndExpression(where, between);
    }

}
