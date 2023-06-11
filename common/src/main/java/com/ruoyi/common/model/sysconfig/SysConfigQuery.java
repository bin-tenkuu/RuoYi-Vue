package com.ruoyi.common.model.sysconfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.entity.SysConfig;
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
public class SysConfigQuery {
    private String configName;
    private String configType;
    private String configKey;
    private LocalDateTime createTimeStart;
    private LocalDateTime createTimeEnd;

    public QueryWrapper<SysConfig> toQuery() {
        return new QueryWrapper<SysConfig>()
                .like(StringUtils.hasLength(configName), SysConfig.CONFIG_NAME, configName)
                .like(StringUtils.hasLength(configType), SysConfig.CONFIG_TYPE, configType)
                .like(StringUtils.hasLength(configKey), SysConfig.CONFIG_KEY, configKey)
                .ge(createTimeStart != null, SysConfig.CREATE_TIME, createTimeStart)
                .le(createTimeEnd != null, SysConfig.CREATE_TIME, createTimeEnd);
    }
}
