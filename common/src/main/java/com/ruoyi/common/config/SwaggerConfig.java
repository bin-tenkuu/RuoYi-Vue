package com.ruoyi.common.config;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.config.page.Pageable;
import com.ruoyi.common.constant.DateConstant;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.SpringDocUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@OpenAPIDefinition(
        info = @Info(
                title = "Demo 接口",
                description = "Demo 相关 API",
                version = "v1.0"
        ),
        security = @SecurityRequirement(name = "Authorization")
)
@SecurityScheme(
        type = SecuritySchemeType.APIKEY,
        name = "Authorization",
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "Bearer "
)
public class SwaggerConfig {
    static {
        SpringDocUtils.getConfig()
                .replaceParameterObjectWithClass(IPage.class, Pageable.class)
                .addFileType(MultipartFile.class)
                .replaceWithSchema(LocalDate.class, new Schema<>()
                        .type("string")
                        .description(DateConstant.DATE_FORMAT)
                        .example("2020-01-01")
                )
                .replaceWithSchema(LocalTime.class, new Schema<>()
                        .type("string")
                        .description(DateConstant.TIME_FORMAT)
                        .example("01:01:01")
                )
                .replaceWithSchema(LocalDateTime.class, new Schema<>()
                        .type("string")
                        .description(DateConstant.DATE_TIME_FORMAT)
                        .example("2020-01-01 01:01:01")
                )
        ;
    }
}
