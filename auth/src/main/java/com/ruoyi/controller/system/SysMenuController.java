package com.ruoyi.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.entity.SysMenu;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.model.R;
import com.ruoyi.common.model.TreeSelect;
import com.ruoyi.common.service.ISysMenuService;
import com.ruoyi.common.util.SecurityUtils;
import com.ruoyi.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class SysMenuController {
    private final ISysMenuService menuService;

    /**
     * 获取菜单列表
     */
    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @GetMapping("/list")
    public R<List<SysMenu>> list(SysMenu menu) {
        List<SysMenu> menus = menuService.selectMenuList(menu, SecurityUtils.getLoginUser().getUserId());
        return R.ok(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:menu:query')")
    @GetMapping(value = "/{menuId}")
    public R<SysMenu> getInfo(@PathVariable Long menuId) {
        return R.ok(menuService.getById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public R<List<TreeSelect>> treeselect(SysMenu menu) {
        List<SysMenu> menus = menuService.selectMenuList(menu, SecurityUtils.getLoginUser().getUserId());
        List<TreeSelect> data = menuService.buildMenuTreeSelect(menus);
        return R.ok(data);
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public R<?> roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysMenu> menus = menuService.selectMenuList(SecurityUtils.getLoginUser().getUserId());
        R<?> ajax = R.ok();
        ajax.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return ajax;
    }

    /**
     * 新增菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:add')")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Boolean> add(@Validated @RequestBody SysMenu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            String message = "新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在";
            return R.fail(message);
        } else if (menu.getIsFrame().equals(UserConstants.YES_FRAME) && !StringUtils.ishttp(menu.getPath())) {
            String message = "新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头";
            return R.fail(message);
        }
        menu.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        return R.ok(menuService.save(menu));
    }

    /**
     * 修改菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:edit')")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Boolean> edit(@Validated @RequestBody SysMenu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            String message = "修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在";
            return R.fail(message);
        } else if (menu.getIsFrame().equals(UserConstants.YES_FRAME) && !StringUtils.ishttp(menu.getPath())) {
            String message = "修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头";
            return R.fail(message);
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            String message = "修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己";
            return R.fail(message);
        }
        menu.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        return R.ok(menuService.updateById(menu));
    }

    /**
     * 删除菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public R<Boolean> remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return R.fail("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return R.fail("菜单已分配,不允许删除");
        }
        return R.ok(menuService.removeById(menuId));
    }
}
