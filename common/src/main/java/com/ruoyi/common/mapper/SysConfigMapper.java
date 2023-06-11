package com.ruoyi.common.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.entity.SysConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 参数配置 数据层
 *
 * @author ruoyi
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    Long selectIdByConfigKey(@Param("configKey") String configKey);

}
