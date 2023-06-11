package com.ruoyi.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.entity.SysDictData;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.model.R;
import com.ruoyi.common.model.query.SysDictDataQuery;
import com.ruoyi.common.service.ISysDictDataService;
import com.ruoyi.common.service.ISysDictTypeService;
import com.ruoyi.common.util.SecurityUtils;
import com.ruoyi.common.util.poi.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/dict/data")
@RequiredArgsConstructor
public class SysDictDataController {
    private final ISysDictDataService dictDataService;

    private final ISysDictTypeService dictTypeService;

    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public R<List<SysDictData>> list(SysDictDataQuery dictData, IPage<SysDictData> page) {
        return R.ok(dictDataService.page(page, dictData.toQuery()));
    }

    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictDataQuery dictData) {
        List<SysDictData> list = dictDataService.list(dictData.toQuery());
        ExcelUtils.writeExcel(response, list, "字典数据", "字典数据", SysDictData.class);
    }

    /**
     * 查询字典数据详细
     */
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictCode}")
    public R<SysDictData> getInfo(@PathVariable Long dictCode) {
        return R.ok(dictDataService.getById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public R<List<SysDictData>> dictType(@PathVariable String dictType) {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (data == null) {
            data = new ArrayList<>();
        }
        return R.ok(data);
    }

    /**
     * 新增字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Boolean> add(@Validated @RequestBody SysDictData dict) {
        dict.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        return R.ok(dictDataService.save(dict));
    }

    /**
     * 修改保存字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Boolean> edit(@Validated @RequestBody SysDictData dict) {
        dict.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        return R.ok(dictDataService.updateById(dict));
    }

    /**
     * 删除字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public R<Boolean> remove(@PathVariable List<Long> dictCodes) {
        return R.ok(dictDataService.removeByIds(dictCodes));
    }
}
