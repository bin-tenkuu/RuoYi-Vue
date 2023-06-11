package com.ruoyi.controller.monitor;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.model.R;
import com.ruoyi.common.model.sysoperlog.SysOperLogQuery;
import com.ruoyi.common.util.poi.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.entity.SysOperLog;
import com.ruoyi.common.service.ISysOperLogService;

/**
 * 操作日志记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/operlog")
public class SysOperlogController {
    @Autowired
    private ISysOperLogService operLogService;

    @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
    @GetMapping("/list")
    public R<List<SysOperLog>> list(SysOperLogQuery operLog, IPage<SysOperLog> page) {
        return R.ok(operLogService.page(page, operLog.toQuery()));
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOperLogQuery operLog) {
        List<SysOperLog> list = operLogService.list(operLog.toQuery());
        ExcelUtils.writeExcel(response, list, "操作日志", "操作日志", SysOperLog.class);
    }

    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/{operIds}")
    public R<Boolean> remove(@PathVariable List<Long> operIds) {
        return R.ok(operLogService.removeByIds(operIds));
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/clean")
    public R<?> clean() {
        operLogService.cleanOperLog();
        return R.ok();
    }
}
