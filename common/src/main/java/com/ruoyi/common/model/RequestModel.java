package com.ruoyi.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 让请求体保持对象格式
 *
 * @author bin
 * @since 2023/01/15
 */
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "请求")
public class RequestModel<T> {
    @Schema(description = "请求参数对象，该处是泛型")
    @Valid
    @NotNull
    private T data;
}
