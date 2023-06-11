package com.ruoyi.common.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.entity.SysLogininfor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @author bin
 * @version 1.0.0
 * @since 2023/6/11
 */
@Getter
@Setter
public class SysLogininforQuery {
    private String userName;
    private String ipaddr;
    private Integer status;
    private LocalDateTime loginTimeStart;
    private LocalDateTime loginTimeEnd;

    public QueryWrapper<SysLogininfor> toQuery() {
        return new QueryWrapper<SysLogininfor>()
                .like(StringUtils.hasLength(userName), SysLogininfor.USER_NAME, userName)
                .like(StringUtils.hasLength(ipaddr), SysLogininfor.IPADDR, ipaddr)
                .eq(status != null, SysLogininfor.STATUS, status)
                .ge(loginTimeStart != null, SysLogininfor.LOGIN_TIME, loginTimeStart)
                .le(loginTimeEnd != null, SysLogininfor.LOGIN_TIME, loginTimeEnd)
                ;
    }
}
