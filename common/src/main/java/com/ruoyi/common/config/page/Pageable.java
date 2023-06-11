
package com.ruoyi.common.config.page;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

import javax.validation.constraints.Min;

/**
 * The type Pageable.
 *
 * @author bnasslahsen
 */
@Data
@ParameterObject
public class Pageable {

    /**
     * The Page.
     */
    @Min(0)
    @Parameter(description = "页码 (1..N)", schema = @Schema(type = "integer", defaultValue = "1"))
    private final Integer page;

    /**
     * The Size.
     */
    @Min(1)
    @Parameter(description = "每页数量", schema = @Schema(type = "integer", defaultValue = "5"))
    private final Integer size;
}
