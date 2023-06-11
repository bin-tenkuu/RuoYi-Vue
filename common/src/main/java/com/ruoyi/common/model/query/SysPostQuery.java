package com.ruoyi.common.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.ruoyi.common.entity.SysOperLog;
import com.ruoyi.common.entity.SysPost;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * @author bin
 * @version 1.0.0
 * @since 2023/6/11
 */
@Getter
@Setter
public class SysPostQuery {
    private String postCode;
    private String postName;
    private Integer status;

    public QueryWrapper<SysPost> toQuery() {
        return new QueryWrapper<SysPost>()
                .like(StringUtils.hasLength(postCode), SysPost.POST_CODE, postCode)
                .like(StringUtils.hasLength(postName), SysPost.POST_NAME, postName)
                .eq(status != null, SysPost.STATUS, status)
                ;
    }
}
