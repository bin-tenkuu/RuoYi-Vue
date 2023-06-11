package com.ruoyi.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.ruoyi.common.annotation.ExcelDict;
import com.ruoyi.common.entity.BaseEntity;
import com.ruoyi.common.util.poi.ExcelUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author ruoyi
 */
@Setter
@Getter
@ExcelIgnoreUnannotated
@ColumnWidth(16)
public class SysLogininfor extends BaseEntity {

    /**
     * ID
     */
    @ExcelProperty(value = "序号")
    private Long infoId;

    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号")
    private String userName;

    /**
     * 登录状态 0成功 1失败
     */
    @ExcelProperty(value = "登录状态", converter = ExcelUtils.DictConverter.class)
    @ExcelDict(key = "0", value = "成功")
    @ExcelDict(key = "1", value = "失败")
    private String status;

    /**
     * 登录IP地址
     */
    @ExcelProperty(value = "登录地址")
    private String ipaddr;

    /**
     * 登录地点
     */
    @ExcelProperty(value = "登录地点")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @ExcelProperty(value = "浏览器")
    private String browser;

    /**
     * 操作系统
     */
    @ExcelProperty(value = "操作系统")
    private String os;

    /**
     * 提示消息
     */
    @ExcelProperty(value = "提示消息")
    private String msg;

    /**
     * 访问时间
     */
    @ExcelProperty(value = "访问时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(30)
    private Date loginTime;

}
