package com.ruoyi.common.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.entity.SysDictType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @author bin
 * @version 1.0.0
 * @since 2023/6/11
 */
@Getter
@Setter
public class SysDictTypeQuery {
    private String dictName;
    private String status;
    private LocalDateTime createTimeStart;
    private LocalDateTime createTimeEnd;

    public QueryWrapper<SysDictType> toQuery() {
        return new QueryWrapper<SysDictType>()
                .like(StringUtils.hasLength(dictName), SysDictType.DICT_NAME, dictName)
                .eq(StringUtils.hasLength(status), SysDictType.STATUS, status)
                .ge(createTimeStart != null, SysDictType.CREATE_TIME, createTimeStart)
                .le(createTimeEnd != null, SysDictType.CREATE_TIME, createTimeEnd)
                ;
    }
}
