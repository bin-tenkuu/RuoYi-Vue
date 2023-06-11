package com.ruoyi.common.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.ruoyi.common.annotation.ExcelDict;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.entity.BaseEntity;

/**
 * 字典数据表 sys_dict_data
 *
 * @author ruoyi
 */
@Setter
@Getter
@ExcelIgnoreUnannotated
@ColumnWidth(16)
public class SysDictData extends BaseEntity {

    /**
     * 字典编码
     */
    @ExcelProperty(value = "字典编码")
    private Long dictCode;

    /**
     * 字典排序
     */
    @ExcelProperty(value = "字典排序")
    private Long dictSort;

    /** 字典标签 */
    @NotBlank(message = "字典标签不能为空") @Size(min = 0, max = 100, message = "字典标签长度不能超过100个字符")
    @ExcelProperty(value = "字典标签")
    private String dictLabel;

    /** 字典键值 */
    @NotBlank(message = "字典键值不能为空") @Size(min = 0, max = 100, message = "字典键值长度不能超过100个字符")
    @ExcelProperty(value = "字典键值")
    private String dictValue;

    /** 字典类型 */
    @NotBlank(message = "字典类型不能为空") @Size(min = 0, max = 100, message = "字典类型长度不能超过100个字符")
    @ExcelProperty(value = "字典类型")
    private String dictType;

    /** 样式属性（其他样式扩展） */
    @Size(min = 0, max = 100, message = "样式属性长度不能超过100个字符")
    private String cssClass;

    /**
     * 表格字典样式
     */
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    @ExcelProperty(value = "是否默认")
    @ExcelDict(key = "Y", value = "是")
    @ExcelDict(key = "N", value = "否")
    private String isDefault;

    /**
     * 状态（0正常 1停用）
     */
    @ExcelProperty(value = "状态")
    @ExcelDict(key = "0", value = "正常")
    @ExcelDict(key = "1", value = "停用")
    private String status;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dictCode", getDictCode())
                .append("dictSort", getDictSort())
                .append("dictLabel", getDictLabel())
                .append("dictValue", getDictValue())
                .append("dictType", getDictType())
                .append("cssClass", getCssClass())
                .append("listClass", getListClass())
                .append("isDefault", getIsDefault())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
