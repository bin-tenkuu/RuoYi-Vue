package com.ruoyi.common.model.sysoperlog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.entity.SysOperLog;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author bin
 * @version 1.0.0
 * @since 2023/6/11
 */
@Getter
@Setter
public class SysOperLogQuery {
    private String title;
    private Integer businessType;
    private List<Integer> businessTypes;
    private String status;
    private String operName;
    private LocalDateTime operTimeStart;
    private LocalDateTime operTimeEnd;

    public QueryWrapper<SysOperLog> toQuery() {
        return new QueryWrapper<SysOperLog>()
                .like(title != null, SysOperLog.TITLE, title)
                .eq(businessType != null, SysOperLog.BUSINESS_TYPE, businessType)
                .in(businessTypes != null, SysOperLog.BUSINESS_TYPE, businessTypes)
                .eq(status != null, SysOperLog.STATUS, status)
                .like(operName != null, SysOperLog.OPER_NAME, operName)
                .ge(operTimeStart != null, SysOperLog.OPER_TIME, operTimeStart)
                .le(operTimeEnd != null, SysOperLog.OPER_TIME, operTimeEnd);

    }
}
