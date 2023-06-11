package com.ruoyi.common.service;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.ResultException;
import com.ruoyi.common.manager.AsyncManager;
import com.ruoyi.common.manager.AsyncFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.ruoyi.common.entity.SysUser;
import com.ruoyi.common.model.login.LoginUser;
import com.ruoyi.common.enums.UserStatus;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysRoleMenuService permissionService;

    @Override
    public LoginUser loadUserByUsername(String username) {
        SysUser user = userService.selectUserByUserName(username);
        String message;
        if (user == null) {
            message = "登录用户：" + username + " 不存在";
            AsyncManager.execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, message));
            throw new ResultException(message);
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            message = "对不起，您的账号：" + username + " 已被删除";
            AsyncManager.execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, message));
            throw new ResultException(message);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            message = "对不起，您的账号：" + username + " 已停用";
            AsyncManager.execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, message));
            throw new ResultException(message);
        }

        return createLoginUser(user);
    }

    public LoginUser createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
