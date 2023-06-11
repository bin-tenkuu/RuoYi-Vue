package com.ruoyi.common.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * 响应信息主体
 *
 * @author ruoyi
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "返回")
public class R<T> {

    @Schema(description = "返回码", example = "200")
    private int code;

    @Schema(description = "返回消息", example = "成功")
    private String msg;

    @Schema(description = "返回结果对象，该处是泛型")
    private T data;

    @Nullable
    @Schema(description = "分页对象")
    private PageModel page;

    @JsonIgnore
    @JsonAnyGetter
    private final HashMap<String, Object> extra = new HashMap<>();

    public void set(String key, Object value) {
        extra.put(key, value);
    }

    public R<T> put(String key, Object value) {
         extra.put(key, value);
         return this;
    }

    public static <T> R<T> ok() {
        return ok((T) null, null);
    }

    public static <T> R<T> ok(T data) {
        return ok(data, null);
    }

    public static <T> R<List<T>> ok(IPage<T> page) {
        return ok(page.getRecords(), page);
    }

    public static <T> R<List<T>> ok(List<T> list, IPage<T> page) {
        return ok(list, new PageModel((int) page.getCurrent(),
                (int) page.getSize(),
                (int) page.getPages(),
                page.getTotal()));
    }

    public static <T> R<T> ok(T data, PageModel page) {
        return restResult(200, "成功", data, page);
    }

    public static <T> R<T> fail() {
        return fail(500, "失败");
    }

    public static <T> R<T> fail(String msg) {
        return fail(500, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(code, msg, null, null);
    }

    private static <T> R<T> restResult(int code, String msg, T data, PageModel page) {
        return new R<>(code, msg, data, page);
    }
}
