package com.ruoyi.common.config;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.security.filter.JwtAuthenticationTokenFilter;
import com.ruoyi.common.security.handle.AuthenticationEntryPointImpl;
import com.ruoyi.common.security.handle.LogoutSuccessHandlerImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;

/**
 * spring security配置
 *
 * @author ruoyi
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    /**
     * 认证失败处理类
     */
    private final AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    private final LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    private final JwtAuthenticationTokenFilter authenticationTokenFilter;

    private final RequestMappingHandlerMapping mapping;

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 有自己的 login ，关闭自带的
                .formLogin().disable()
                .httpBasic().disable()
                // 禁用HTTP响应标头
                .headers().cacheControl().disable().and()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 注解标记允许匿名访问的url
                .antMatchers(
                        getUrls()
                )
                .permitAll()
                // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                .antMatchers(
                        "/login",
                        "/register",
                        "/captchaImage",
                        "/swagger/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/*/api-docs"
                ).permitAll()
                // 静态资源，可匿名访问
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/*.ico",
                        "/profile/**"
                ).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable().and()
                // 添加Logout filter
                .logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler).and()
                // 添加JWT filter
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * 允许匿名访问的地址
     */
    private String[] getUrls() {
        val urls = new HashSet<String>();
        for (val entry : mapping.getHandlerMethods().entrySet()) {
            val info = entry.getKey();
            val handlerMethod = entry.getValue();

            // 获取方法上边的注解 替代path variable 为 *
            Anonymous anonymous = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Anonymous.class);
            if (anonymous != null) {
                urls.addAll(info.getPatternsCondition().getPatterns());
            }
        }
        return urls.toArray(new String[0]);
    }
}
