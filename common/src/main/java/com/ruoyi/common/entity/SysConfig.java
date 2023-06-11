package com.ruoyi.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.ExcelDict;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 参数配置表 sys_config
 *
 * @author ruoyi
 */
@Setter
@Getter
@ExcelIgnoreUnannotated
@ToString
@ColumnWidth(16)
@TableName(value = "sys_config", resultMap = "BaseResultMap")
public class SysConfig extends BaseEntity {

    /**
     * 参数主键
     */
    @TableId(value = "config_id", type = IdType.AUTO)
    @ExcelProperty(value = "参数主键")
    private Long id;

    /**
     * 参数名称
     */
    @TableField(value = "config_name")
    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
    @ExcelProperty(value = "参数名称")
    private String configName;


    /**
     * 参数键名
     */
    @TableField(value = "config_key")
    @NotBlank(message = "参数键名长度不能为空")
    @Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
    @ExcelProperty(value = "参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @TableField(value = "config_value")
    @NotBlank(message = "参数键值不能为空")
    @Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
    @ExcelProperty(value = "参数键值")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    @TableField(value = "config_type")
    @ExcelProperty(value = "系统内置")
    @ExcelDict(key = "Y", value = "是")
    @ExcelDict(key = "N", value = "否")
    private String configType;

    public static final String CONFIG_NAME = "config_name";
    public static final String CONFIG_KEY = "config_key";
    public static final String CONFIG_VALUE = "config_value";
    public static final String CONFIG_TYPE = "config_type";

}
