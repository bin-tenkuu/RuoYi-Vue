package com.ruoyi.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.ruoyi.common.entity.SysOperLog;
import com.ruoyi.common.mapper.SysOperLogMapper;
import com.ruoyi.common.service.ISysOperLogService;

/**
 * 操作日志 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog>
        implements ISysOperLogService {

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        baseMapper.clear();
    }
}
