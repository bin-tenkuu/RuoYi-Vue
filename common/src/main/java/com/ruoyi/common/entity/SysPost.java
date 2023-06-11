package com.ruoyi.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.ExcelDict;
import com.ruoyi.common.util.poi.IntDictConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 岗位表 sys_post
 *
 * @author ruoyi
 */
@Getter
@Setter
@ToString
@ExcelIgnoreUnannotated
@ColumnWidth(16)
@TableName(value = "sys_post", resultMap = "BaseResultMap")
public class SysPost extends BaseEntity {

    /**
     * 岗位序号
     */
    @TableId(value = "post_id", type = IdType.AUTO)
    @ExcelProperty(value = "岗位序号")
    private Long postId;

    /**
     * 岗位编码
     */
    @TableField(value = "post_code")
    @NotBlank(message = "岗位编码不能为空")
    @Size(max = 64, message = "岗位编码长度不能超过64个字符")
    @ExcelProperty(value = "岗位编码")
    private String postCode;

    /**
     * 岗位名称
     */
    @TableField(value = "post_name")
    @NotBlank(message = "岗位名称不能为空")
    @Size(max = 50, message = "岗位名称长度不能超过50个字符")
    @ExcelProperty(value = "岗位名称")
    private String postName;

    /**
     * 岗位排序
     */
    @TableField(value = "post_sort")
    @NotNull(message = "显示顺序不能为空")
    @ExcelProperty(value = "岗位排序")
    private Integer postSort;

    /**
     * 状态（0正常 1停用）
     */
    @TableField(value = "status")
    @ExcelProperty(value = "状态", converter = IntDictConverter.class)
    @ExcelDict(key = 0, value = "正常")
    @ExcelDict(key = 1, value = "停用")
    private Integer status;

    public static final String POST_ID = "post_id";
    public static final String POST_CODE = "post_code";
    public static final String POST_NAME = "post_name";
    public static final String POST_SORT = "post_sort";
    public static final String STATUS = "status";

}
