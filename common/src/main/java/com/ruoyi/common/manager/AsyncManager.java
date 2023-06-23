package com.ruoyi.common.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 异步任务管理器
 *
 * @author ruoyi
 */
@Slf4j
@Component
public class AsyncManager implements DisposableBean {
    /**
     * 异步操作任务调度线程池
     */
    private static final ScheduledExecutorService EXECUTOR = new ScheduledThreadPoolExecutor(1);

    /**
     * 执行任务,操作延迟10毫秒
     *
     * @param task 任务
     */
    public static void execute(Runnable task) {
        EXECUTOR.schedule(task, 10, TimeUnit.MILLISECONDS);
    }

    @Override
    public void destroy() {
        try {
            log.info("====关闭后台任务任务线程池====");
            EXECUTOR.shutdown();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
