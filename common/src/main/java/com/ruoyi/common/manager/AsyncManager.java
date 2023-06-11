package com.ruoyi.common.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.util.Threads;
import com.ruoyi.common.util.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

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
    private static final ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

    /**
     * 执行任务,操作延迟10毫秒
     *
     * @param task 任务
     */
    public static void execute(Runnable task) {
        executor.schedule(task, 10, TimeUnit.MILLISECONDS);
    }

    @Override
    public void destroy() {
        try {
            log.info("====关闭后台任务任务线程池====");
            executor.shutdown();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
