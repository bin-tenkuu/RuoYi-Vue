package com.ruoyi.common.config;

import com.ruoyi.common.config.filter.RepeatableFilter;
import com.ruoyi.common.config.filter.XssFilter;
import com.ruoyi.common.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.DispatcherType;
import java.util.HashMap;

/**
 * Filter配置
 *
 * @author ruoyi
 */
@Getter
@Setter
@Component
@ConditionalOnProperty(value = "xss.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "xss")
public class FilterConfig {
    private boolean enabled;
    private String excludes;
    private String urlPatterns;

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        val registration = new FilterRegistrationBean<XssFilter>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
        registration.setName("xssFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        val initParameters = new HashMap<String, String>();
        initParameters.put("excludes", excludes);
        registration.setInitParameters(initParameters);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<RepeatableFilter> someFilterRegistration() {
        val registration = new FilterRegistrationBean<RepeatableFilter>();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }

}
