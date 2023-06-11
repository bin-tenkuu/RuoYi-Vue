package com.ruoyi.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
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

import java.time.LocalDateTime;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author ruoyi
 */
@Setter
@Getter
@ToString
@ExcelIgnoreUnannotated
@ColumnWidth(16)
@TableName(value = "sys_logininfor", resultMap = "BaseResultMap")
public class SysLogininfor {

    /**
     * ID
     */
    @TableId(value = "info_id", type = IdType.AUTO)
    @ExcelProperty(value = "序号")
    private Long infoId;

    /**
     * 用户账号
     */
    @TableField(value = "user_name")
    @ExcelProperty(value = "用户账号")
    private String userName;

    /**
     * 登录IP地址
     */
    @TableField(value = "ipaddr")
    @ExcelProperty(value = "登录地址")
    private String ipaddr;

    /**
     * 登录地点
     */
    @TableField(value = "login_location")
    @ExcelProperty(value = "登录地点")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @TableField(value = "browser")
    @ExcelProperty(value = "浏览器")
    private String browser;

    /**
     * 操作系统
     */
    @TableField(value = "os")
    @ExcelProperty(value = "操作系统")
    private String os;

    /**
     * 登录状态 0成功 1失败
     */
    @TableField(value = "status")
    @ExcelProperty(value = "登录状态", converter = IntDictConverter.class)
    @ExcelDict(key = 0, value = "成功")
    @ExcelDict(key = 1, value = "失败")
    private Integer status;

    /**
     * 提示消息
     */
    @TableField(value = "msg")
    @ExcelProperty(value = "提示消息")
    private String msg;

    /**
     * 访问时间
     */
    @TableField(value = "login_time")
    @ExcelProperty(value = "访问时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(30)
    private LocalDateTime loginTime;

    public static final String INFO_ID = "info_id";
    public static final String USER_NAME = "user_name";
    public static final String IPADDR = "ipaddr";
    public static final String LOGIN_LOCATION = "login_location";
    public static final String BROWSER = "browser";
    public static final String OS = "os";
    public static final String STATUS = "status";
    public static final String MSG = "msg";
    public static final String LOGIN_TIME = "login_time";

}
