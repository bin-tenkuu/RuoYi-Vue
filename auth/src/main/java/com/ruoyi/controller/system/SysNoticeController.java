package com.ruoyi.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.entity.SysNotice;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.model.R;
import com.ruoyi.common.model.query.SysNoticeQuery;
import com.ruoyi.common.service.ISysNoticeService;
import com.ruoyi.common.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告 信息操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/notice")
@RequiredArgsConstructor
public class SysNoticeController {
    private final ISysNoticeService noticeService;

    /**
     * 获取通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public R<List<SysNotice>> list(SysNoticeQuery notice, IPage<SysNotice> page) {
        return R.ok(noticeService.page(page, notice.toQuery()));
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public R<SysNotice> getInfo(@PathVariable Long noticeId) {
        return R.ok(noticeService.getById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Boolean> add(@Validated @RequestBody SysNotice notice) {
        notice.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        return R.ok(noticeService.save(notice));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Boolean> edit(@Validated @RequestBody SysNotice notice) {
        notice.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        return R.ok(noticeService.updateById(notice));
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public R<Boolean> remove(@PathVariable List<Long> noticeIds) {
        return R.ok(noticeService.removeByIds(noticeIds));
    }
}
