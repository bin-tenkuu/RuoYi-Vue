package com.ruoyi.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * Entity基类
 *
 * @author ruoyi
 */
@Setter
@Getter
public class BaseEntity {
    @TableField("create_by")
    @Schema(description = "创建者")
    protected String createBy;
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    protected LocalDateTime createTime;
    @TableField("update_by")
    @Schema(description = "更新者")
    protected String updateBy;
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    protected LocalDateTime updateTime;
    @TableField("remark")
    @Schema(description = "备注")
    protected String remark;

    /**
     * 请求参数
     */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    protected Map<String, Object> params;

    public static final String CREATE_BY = "create_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_BY = "update_by";
    public static final String UPDATE_TIME = "update_time";
}
