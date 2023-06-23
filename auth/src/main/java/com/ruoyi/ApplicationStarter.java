package com.ruoyi;

import cn.hutool.extra.spring.SpringUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication
@EnableScheduling
@Import(SpringUtil.class)
public class ApplicationStarter implements ApplicationListener<ContextRefreshedEvent> {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

    @Override
    public void onApplicationEvent(final @NotNull ContextRefreshedEvent event) {
        System.out.println("启动成功");
    }
}
