package com.ruoyi.controller.system;

import java.util.List;

import com.ruoyi.common.model.R;
import com.ruoyi.common.model.TreeSelect;
import com.ruoyi.common.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.entity.SysMenu;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.util.StringUtils;
import com.ruoyi.common.service.ISysMenuService;

/**
 * 菜单信息
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {
    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取菜单列表
     */
    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @GetMapping("/list")
    public R list(SysMenu menu)
    {
        List<SysMenu> menus = menuService.selectMenuList(menu, SecurityUtils.getLoginUser().getUserId());
        return R.ok(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:menu:query')")
    @GetMapping(value = "/{menuId}")
    public R getInfo(@PathVariable Long menuId)
    {
        SysMenu data = menuService.selectMenuById(menuId);
        return R.ok(data);
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public R treeselect(SysMenu menu)
    {
        List<SysMenu> menus = menuService.selectMenuList(menu, SecurityUtils.getLoginUser().getUserId());
        List<TreeSelect> data = menuService.buildMenuTreeSelect(menus);
        return R.ok(data);
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public R roleMenuTreeselect(@PathVariable("roleId") Long roleId)
    {
        List<SysMenu> menus = menuService.selectMenuList(SecurityUtils.getLoginUser().getUserId());
        R ajax = R.ok();
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
    public R add(@Validated @RequestBody SysMenu menu)
    {
        if (!menuService.checkMenuNameUnique(menu))
        {
            String message = "新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在";
            return R.fail(message);
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath()))
        {
            String message = "新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头";
            return R.fail(message);
        }
        menu.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        return menuService.insertMenu(menu) > 0 ? R.ok() : R.fail();
    }

    /**
     * 修改菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:edit')")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@Validated @RequestBody SysMenu menu)
    {
        if (!menuService.checkMenuNameUnique(menu))
        {
            String message = "修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在";
            return R.fail(message);
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath()))
        {
            String message = "修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头";
            return R.fail(message);
        }
        else if (menu.getMenuId().equals(menu.getParentId()))
        {
            String message = "修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己";
            return R.fail(message);
        }
        menu.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        return menuService.updateMenu(menu) > 0 ? R.ok() : R.fail();
    }

    /**
     * 删除菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public R remove(@PathVariable("menuId") Long menuId)
    {
        if (menuService.hasChildByMenuId(menuId))
        {
            return R.fail("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId))
        {
            return R.fail("菜单已分配,不允许删除");
        }
        return menuService.deleteMenuById(menuId) > 0 ? R.ok() : R.fail();
    }
}
