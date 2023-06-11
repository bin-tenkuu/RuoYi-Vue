package com.ruoyi.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.entity.SysConfig;
import com.ruoyi.common.exception.ResultException;
import com.ruoyi.common.mapper.SysConfigMapper;
import com.ruoyi.common.service.ISysConfigService;
import com.ruoyi.common.service.RedisService;
import com.ruoyi.common.util.StringUtils;
import com.ruoyi.common.util.text.Convert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 参数配置 服务层实现
 *
 * @author ruoyi
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig>
        implements ISysConfigService, InitializingBean {
    private final RedisService redisService;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @Override
    public void afterPropertiesSet() {
        loadingConfigCache();
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        String configValue = redisService.getValue(getCacheKey(configKey), String.class);
        if (StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }
        SysConfig retConfig = getOne(new QueryWrapper<SysConfig>()
                .eq(SysConfig.CONFIG_KEY, configKey), false);
        if (retConfig != null) {
            redisService.setValue(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    @Override
    public boolean selectCaptchaEnabled() {
        String captchaEnabled = selectConfigByKey("sys.account.captchaEnabled");
        if (StringUtils.isEmpty(captchaEnabled)) {
            return true;
        }
        return Convert.toBool(captchaEnabled);
    }

    /**
     * 新增参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(SysConfig config) {
        int row = baseMapper.insert(config);
        if (row > 0) {
            redisService.setValue(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(SysConfig config) {
        SysConfig temp = baseMapper.selectById(config.getId());
        if (!StringUtils.equals(temp.getConfigKey(), config.getConfigKey())) {
            redisService.delete(getCacheKey(temp.getConfigKey()));
        }

        int row = baseMapper.updateById(config);
        if (row > 0) {
            redisService.setValue(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     */
    @Override
    public void deleteConfigByIds(Long[] configIds) {
        for (Long configId : configIds) {
            SysConfig config = getById(configId);
            if (UserConstants.YES.equals(config.getConfigType())) {
                throw new ResultException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
            baseMapper.deleteById(configId);
            redisService.delete(getCacheKey(config.getConfigKey()));
        }
    }

    /**
     * 加载参数缓存数据
     */
    private void loadingConfigCache() {
        List<SysConfig> configsList = baseMapper.selectList(null);
        for (SysConfig config : configsList) {
            redisService.setValue(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

    /**
     * 清空参数缓存数据
     */
    private void clearConfigCache() {
        Collection<String> keys = redisService.keys(CacheConstants.SYS_CONFIG_KEY + "*");
        redisService.delete(keys);
    }

    /**
     * 重置参数缓存数据
     */
    @Override
    public void resetConfigCache() {
        clearConfigCache();
        loadingConfigCache();
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public boolean checkConfigKeyUnique(SysConfig config) {
        Long configId = config.getId() == null ? -1L : config.getId();
        Long info = baseMapper.selectIdByConfigKey(config.getConfigKey());
        return info == null || info.equals(configId);
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return CacheConstants.SYS_CONFIG_KEY + configKey;
    }
}
