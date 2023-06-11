package com.ruoyi.common.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.entity.SysOperLog;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

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
    private Integer status;
    private String operName;
    private LocalDateTime operTimeStart;
    private LocalDateTime operTimeEnd;

    public QueryWrapper<SysOperLog> toQuery() {
        return new QueryWrapper<SysOperLog>()
                .like(StringUtils.hasLength(title), SysOperLog.TITLE, title)
                .eq(businessType != null, SysOperLog.BUSINESS_TYPE, businessType)
                .in(businessTypes != null && !businessTypes.isEmpty(), SysOperLog.BUSINESS_TYPE, businessTypes)
                .eq(status != null, SysOperLog.STATUS, status)
                .like(StringUtils.hasLength(operName), SysOperLog.OPER_NAME, operName)
                .ge(operTimeStart != null, SysOperLog.OPER_TIME, operTimeStart)
                .le(operTimeEnd != null, SysOperLog.OPER_TIME, operTimeEnd);

    }
}
