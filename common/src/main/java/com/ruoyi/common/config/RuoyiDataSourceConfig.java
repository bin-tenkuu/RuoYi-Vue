package com.ruoyi.common.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author 杨启东
 * @since 2023/05/19
 */
@Component
@EnableConfigurationProperties({MybatisPlusProperties.class})
@MapperScan(value = "com.ruoyi.common.mapper", sqlSessionTemplateRef = "ruoyiSqlSessionTemplate")
@RequiredArgsConstructor
public class RuoyiDataSourceConfig {

    private final MybatisPlusProperties properties;
    private final Optional<DataPermissionHandler> dataPermissionHandler;

    private void modify(MybatisSqlSessionFactoryBean bean) {
        val globalConfig = new GlobalConfig();
        BeanUtils.copyProperties(properties.getGlobalConfig(), globalConfig);
        globalConfig.setMetaObjectHandler(new TimeMetaObjectHandler());
        bean.setGlobalConfig(globalConfig);
        val configuration = new MybatisConfiguration();
        BeanUtils.copyProperties(properties.getConfiguration(), configuration);
        bean.setConfiguration(configuration);
    }

    private static MybatisPlusInterceptor interceptor(InnerInterceptor... interceptors) {
        final MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        for (InnerInterceptor innerInterceptor : interceptors) {
            if (innerInterceptor != null) {
                interceptor.addInnerInterceptor(innerInterceptor);
            }
        }
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    // region ruoyi

    @Bean(name = "ruoyiDataSource")
    @ConfigurationProperties("spring.datasource-ruoyi")
    public HikariDataSource ruoyiDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "ruoyiSqlSessionFactory")
    public SqlSessionFactory ruoyiSqlSessionFactory(
            @Qualifier("ruoyiDataSource") DataSource dataSource
    ) throws Exception {
        final MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
        modify(bean);
        bean.setPlugins(interceptor(
                new DataPermissionInterceptor(dataPermissionHandler.orElse(null))
        ));
        return bean.getObject();
    }

    @Bean(name = "ruoyiSqlSessionTemplate")
    public SqlSessionTemplate ruoyiSqlSessionTemplate(
            @Qualifier("ruoyiSqlSessionFactory") SqlSessionFactory sqlSessionFactory
    ) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // endregion

    public static class TimeMetaObjectHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
            this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        }
    }
}
