package com.ruoyi.common.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.entity.SysOperLog;

/**
 * 操作日志 服务层
 *
 * @author ruoyi
 */
public interface ISysOperLogService extends IService<SysOperLog> {

    /**
     * 清空操作日志
     */
    void cleanOperLog();
}
