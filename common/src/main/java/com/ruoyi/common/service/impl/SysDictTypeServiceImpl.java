package com.ruoyi.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.entity.SysDictData;
import com.ruoyi.common.entity.SysDictType;
import com.ruoyi.common.mapper.SysDictDataMapper;
import com.ruoyi.common.mapper.SysDictTypeMapper;
import com.ruoyi.common.service.ISysDictTypeService;
import com.ruoyi.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典 业务层处理
 *
 * @author ruoyi
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType>
        implements ISysDictTypeService {

    private final SysDictDataMapper dictDataMapper;

    private static final HashMap<String, List<SysDictData>> DICT_CACHE = new HashMap<>();

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init() {
        loadingDictCache();
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        List<SysDictData> dictDatas = DICT_CACHE.get(dictType);
        if (StringUtils.isNotEmpty(dictDatas)) {
            return dictDatas;
        }
        dictDatas = dictDataMapper.selectByType(dictType);
        if (StringUtils.isNotEmpty(dictDatas)) {
            DICT_CACHE.put(dictType, dictDatas);
            return dictDatas;
        }
        return null;
    }

    /**
     * 加载字典缓存数据
     */
    @Override
    public void loadingDictCache() {
        Map<String, List<SysDictData>> dictDataMap = dictDataMapper.selectByStatus(0)
                .stream().collect(Collectors.groupingBy(SysDictData::getDictType));
        DICT_CACHE.putAll(dictDataMap);
    }

    /**
     * 清空字典缓存数据
     */
    @Override
    public void clearDictCache() {
        DICT_CACHE.clear();
    }

    /**
     * 重置字典缓存数据
     */
    @Override
    public void resetDictCache() {
        clearDictCache();
        loadingDictCache();
    }

    /**
     * 新增保存字典类型信息
     *
     * @param dict 字典类型信息
     * @return 结果
     */
    @Override
    public int insertDictType(SysDictType dict) {
        int row = baseMapper.insert(dict);
        if (row > 0) {
            DICT_CACHE.put(dict.getDictType(), null);
        }
        return row;
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dict 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDictType(SysDictType dict) {
        SysDictType oldDict = baseMapper.selectById(dict.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dict.getDictType());
        int row = baseMapper.updateById(dict);
        if (row > 0) {
            List<SysDictData> dictDatas = dictDataMapper.selectByType(dict.getDictType());
            DICT_CACHE.put(dict.getDictType(), dictDatas);
        }
        return row;
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public boolean checkDictTypeUnique(SysDictType dict) {
        long dictId = dict.getDictId() == null ? -1L : dict.getDictId();
        SysDictType dictType = baseMapper.checkDictTypeUnique(dict.getDictType());
        return dictType == null || dictType.getDictId().equals(dictId);
    }
}
