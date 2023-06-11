package com.ruoyi.common.util.poi;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.ruoyi.common.annotation.ExcelDict;
import com.ruoyi.common.annotation.ExcelDicts;
import lombok.val;

/**
 * @author bin
 * @version 1.0.0
 * @since 2023/6/11
 */
public class IntDictConverter implements Converter<Integer> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        val dicts = contentProperty.getField().getAnnotation(ExcelDicts.class);
        if (value != null) {
            for (ExcelDict dict : dicts.value()) {
                if (value.equals(dict.key())) {
                    return new WriteCellData<>(dict.value());
                }
            }
        }
        return new WriteCellData<>(dicts.defaultDict().value());
    }

    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        val dicts = contentProperty.getField().getAnnotation(ExcelDicts.class);
        val stringValue = cellData.getStringValue();
        for (ExcelDict dict : dicts.value()) {
            if (dict.value().equals(stringValue)) {
                return dict.key();
            }
        }
        return dicts.defaultDict().key();
    }
}
