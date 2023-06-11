package com.ruoyi.controller.system;

import com.ruoyi.common.entity.SysMenu;
import com.ruoyi.common.entity.SysUser;
import com.ruoyi.common.model.R;
import com.ruoyi.common.model.login.LoginBody;
import com.ruoyi.common.model.sysmenu.RouterVo;
import com.ruoyi.common.service.ISysMenuService;
import com.ruoyi.common.service.SysLoginService;
import com.ruoyi.common.service.SysRoleMenuService;
import com.ruoyi.common.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
@RequiredArgsConstructor
public class SysLoginController {
    private final SysLoginService loginService;

    private final ISysMenuService menuService;

    private final SysRoleMenuService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public R<String> login(@RequestBody LoginBody loginBody) {
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        return R.ok(token);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public R<?> getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        R<?> ajax = R.ok();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R<List<RouterVo>> getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        List<RouterVo> data = menuService.buildMenus(menus);
        return R.ok(data);
    }
}
