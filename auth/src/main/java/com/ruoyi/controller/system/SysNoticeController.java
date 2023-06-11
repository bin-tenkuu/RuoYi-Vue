package com.ruoyi.controller.system;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.model.R;
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
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.entity.SysNotice;
import com.ruoyi.common.service.ISysNoticeService;

/**
 * 公告 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController {
    @Autowired
    private ISysNoticeService noticeService;

    /**
     * 获取通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public R list(SysNotice notice)
    {
        PageUtils.startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return R.ok().put("rows", list).put("total", new PageInfo(list).getTotal());
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public R getInfo(@PathVariable Long noticeId)
    {
        SysNotice data = noticeService.selectNoticeById(noticeId);
        return R.ok(data);
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public R add(@Validated @RequestBody SysNotice notice)
    {
        notice.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        return noticeService.insertNotice(notice) > 0 ? R.ok() : R.fail();
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@Validated @RequestBody SysNotice notice)
    {
        notice.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        return noticeService.updateNotice(notice) > 0 ? R.ok() : R.fail();
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public R remove(@PathVariable Long[] noticeIds)
    {
        return noticeService.deleteNoticeByIds(noticeIds) > 0 ? R.ok() : R.fail();
    }
}
