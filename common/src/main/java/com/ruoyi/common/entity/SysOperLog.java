package com.ruoyi.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.ExcelDict;
import com.ruoyi.common.util.poi.ExcelUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 操作日志记录表 oper_log
 *
 * @author ruoyi
 */
@Setter
@Getter
@ExcelIgnoreUnannotated
@ColumnWidth(16)
@TableName(value = "sys_oper_log", resultMap = "BaseResultMap")
public class SysOperLog {

    /**
     * 日志主键
     */
    @TableId(value = "oper_id", type = IdType.AUTO)
    @ExcelProperty(value = "操作序号")
    private Long id;

    /**
     * 操作模块
     */
    @TableField(value = "title")
    @ExcelProperty(value = "操作模块")
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @TableField(value = "business_type")
    @ExcelProperty(value = "业务类型", converter = ExcelUtils.DictConverter.class)
    @ExcelDict(key = "0", value = "其它")
    @ExcelDict(key = "1", value = "新增")
    @ExcelDict(key = "2", value = "修改")
    @ExcelDict(key = "3", value = "删除")
    @ExcelDict(key = "4", value = "授权")
    @ExcelDict(key = "5", value = "导出")
    @ExcelDict(key = "6", value = "导入")
    @ExcelDict(key = "7", value = "强退")
    @ExcelDict(key = "8", value = "生成代码")
    @ExcelDict(key = "9", value = "清空数据")
    private Integer businessType;

    /**
     * 业务类型数组
     */
    @TableField(exist = false)
    private Integer[] businessTypes;

    /**
     * 请求方法
     */
    @TableField(value = "method")
    @ExcelProperty(value = "请求方法")
    private String method;

    /**
     * 请求方式
     */
    @TableField(value = "request_method")
    @ExcelProperty(value = "请求方式")
    private String requestMethod;

    /**
     * 操作人员
     */
    @TableField(value = "oper_name")
    @ExcelProperty(value = "操作人员")
    private String operName;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 请求url
     */
    @TableField(value = "oper_url")
    @ExcelProperty(value = "请求地址")
    private String operUrl;

    /**
     * 操作地址
     */
    @TableField(value = "oper_ip")
    @ExcelProperty(value = "操作地址")
    private String operIp;

    /**
     * 操作地点
     */
    @TableField(value = "oper_location")
    @ExcelProperty(value = "操作地点")
    private String operLocation;

    /**
     * 请求参数
     */
    @TableField(value = "oper_param")
    @ExcelProperty(value = "请求参数")
    private String operParam;

    /**
     * 返回参数
     */
    @TableField(value = "json_result")
    @ExcelProperty(value = "返回参数")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @TableField(value = "status")
    @ExcelProperty(value = "状态", converter = ExcelUtils.DictConverter.class)
    @ExcelDict(key = "0", value = "正常")
    @ExcelDict(key = "1", value = "异常")
    private Integer status;

    /**
     * 错误消息
     */
    @TableField(value = "error_msg")
    @ExcelProperty(value = "错误消息")
    private String errorMsg;

    /**
     * 操作时间
     */
    @TableField(value = "oper_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "操作时间")
    @ColumnWidth(30)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operTime;

    /**
     * 消耗时间
     */
    @TableField(value = "cost_time")
    @ExcelProperty(value = "消耗时间")
    private Long costTime;

    public static final String TITLE = "title";
    public static final String BUSINESS_TYPE = "business_type";
    public static final String METHOD = "method";
    public static final String REQUEST_METHOD = "request_method";
    public static final String OPER_NAME = "oper_name";
    public static final String DEPT_NAME = "dept_name";
    public static final String OPER_URL = "oper_url";
    public static final String OPER_IP = "oper_ip";
    public static final String OPER_LOCATION = "oper_location";
    public static final String OPER_PARAM = "oper_param";
    public static final String JSON_RESULT = "json_result";
    public static final String STATUS = "status";
    public static final String ERROR_MSG = "error_msg";
    public static final String OPER_TIME = "oper_time";
    public static final String COST_TIME = "cost_time";

}
