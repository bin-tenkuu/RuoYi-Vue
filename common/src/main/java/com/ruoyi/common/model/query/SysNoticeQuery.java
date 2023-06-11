package com.ruoyi.common.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.entity.SysDictType;
import com.ruoyi.common.entity.SysNotice;
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
public class SysNoticeQuery {
    private String noticeTitle;
    private String noticeType;
    protected String createBy;

    public QueryWrapper<SysNotice> toQuery() {
        return new QueryWrapper<SysNotice>()
                .like(StringUtils.hasLength(noticeTitle), SysNotice.NOTICE_TITLE, noticeTitle)
                .eq(StringUtils.hasLength(noticeType), SysNotice.NOTICE_TYPE, noticeType)
                .like(StringUtils.hasLength(createBy), SysNotice.CREATE_BY, createBy)
                ;
    }
}
