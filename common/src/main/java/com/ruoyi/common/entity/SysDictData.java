package com.ruoyi.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.ExcelDict;
import com.ruoyi.common.util.poi.IntDictConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 字典数据表 sys_dict_data
 *
 * @author ruoyi
 */
@Setter
@Getter
@ToString
@ExcelIgnoreUnannotated
@ColumnWidth(16)
@TableName(value = "sys_dict_data", resultMap = "BaseResultMap")
public class SysDictData extends BaseEntity {

    /**
     * 字典编码
     */
    @TableId(value = "dict_code")
    @ExcelProperty(value = "字典编码")
    private Long dictCode;

    /**
     * 字典排序
     */
    @TableField(value = "dict_sort")
    @ExcelProperty(value = "字典排序")
    private Integer dictSort;

    /**
     * 字典标签
     */
    @TableField(value = "dict_label")
    @NotBlank(message = "字典标签不能为空")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    @ExcelProperty(value = "字典标签")
    private String dictLabel;

    /**
     * 字典键值
     */
    @TableField(value = "dict_value")
    @NotBlank(message = "字典键值不能为空")
    @Size(max = 100, message = "字典键值长度不能超过100个字符")
    @ExcelProperty(value = "字典键值")
    private String dictValue;

    /**
     * 字典类型
     */
    @TableField(value = "dict_type")
    @NotBlank(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型长度不能超过100个字符")
    @ExcelProperty(value = "字典类型")
    private String dictType;

    /**
     * 样式属性（其他样式扩展）
     */
    @TableField(value = "css_class")
    @Size(max = 100, message = "样式属性长度不能超过100个字符")
    private String cssClass;

    /**
     * 表格字典样式
     */
    @TableField(value = "list_class")
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    @TableField(value = "is_default")
    @ExcelProperty(value = "是否默认", converter = IntDictConverter.class)
    @ExcelDict(key = 1, value = "是")
    @ExcelDict(key = 0, value = "否")
    private Integer isDefault;

    /**
     * 状态（0正常 1停用）
     */
    @TableField(value = "status")
    @ExcelProperty(value = "状态", converter = IntDictConverter.class)
    @ExcelDict(key = 0, value = "正常")
    @ExcelDict(key = 1, value = "停用")
    private Integer status;

    public static final String DICT_CODE = "dict_code";
    public static final String DICT_SORT = "dict_sort";
    public static final String DICT_LABEL = "dict_label";
    public static final String DICT_VALUE = "dict_value";
    public static final String DICT_TYPE = "dict_type";
    public static final String CSS_CLASS = "css_class";
    public static final String LIST_CLASS = "list_class";
    public static final String IS_DEFAULT = "is_default";
    public static final String STATUS = "status";

}
