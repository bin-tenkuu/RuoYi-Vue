package com.ruoyi.common.config.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author bin
 * @since 2023/01/15
 */
@Component
@ConditionalOnClass(value = IPage.class)
@Import({MybatisPageResolver.class})
public class MybatisPageWebMvcConfigurer implements WebMvcConfigurer {
    private final MybatisPageResolver pageResolver;

    public MybatisPageWebMvcConfigurer(final MybatisPageResolver pageResolver) {
        this.pageResolver = pageResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(pageResolver);
    }
}
