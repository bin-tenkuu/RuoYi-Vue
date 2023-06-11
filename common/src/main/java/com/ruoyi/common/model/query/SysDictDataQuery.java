package com.ruoyi.common.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.entity.SysDictData;
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
public class SysDictDataQuery {

    private String dictLabel;
    private String dictType;
    private Integer status;

    public QueryWrapper<SysDictData> toQuery() {
        return new QueryWrapper<SysDictData>()
                .like(StringUtils.hasLength(dictLabel), SysDictData.DICT_LABEL, dictLabel)
                .like(StringUtils.hasLength(dictType), SysDictData.DICT_TYPE, dictType)
                .eq(status != null, SysDictData.STATUS, status)
                .orderBy(true, true, SysDictData.DICT_SORT)
                ;
    }
}
