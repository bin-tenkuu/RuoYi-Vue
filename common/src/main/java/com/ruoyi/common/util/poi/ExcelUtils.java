package com.ruoyi.common.util.poi;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.ruoyi.common.annotation.ExcelDict;
import com.ruoyi.common.annotation.ExcelDicts;
import com.ruoyi.common.exception.ResultException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author bin
 * @version 1.0.0
 * @since 2023/6/10
 */
@Slf4j
public class ExcelUtils {

    /**
     * 读取excel文件
     */
    public static <T> List<T> readExcel(InputStream ins, Class<T> clazz) {
        //sheet 设置处理的工作簿 headRowNumber 设置从excel第几行开始读取
        return EasyExcel.read(ins).head(clazz).sheet().headRowNumber(1).doReadSync();//第0行一般是表头，从第1行开始读取
    }

    /**
     * 向浏览器输出excel文件
     *
     * @param response  HttpServletResponse
     * @param sheetName 输出的文件名称 excel的名称
     * @param clazz     输出数据的模板
     */
    public static <T> void writeExcel(HttpServletResponse response, Class<T> clazz, String fileName, String sheetName) {
        writeExcel(response, Collections.emptyList(), fileName, sheetName, clazz);
    }

    /**
     * 向浏览器输出excel文件
     *
     * @param response  HttpServletResponse
     * @param data      输出的数据
     * @param fileName  输出的文件名称 excel的名称
     * @param sheetName 输出的excel的sheet的名称 也就是页的名称
     * @param clazz     输出数据的模板
     */
    public static <T> void writeExcel(HttpServletResponse response, List<T> data, String fileName, String sheetName,
            Class<T> clazz) {
        //表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置内容靠左对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        EasyExcel.write(getOutputStream(fileName, response), clazz)
                .excelType(ExcelTypeEnum.XLSX).sheet(sheetName)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .doWrite(data);
    }

    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        try {
            return response.getOutputStream();
        } catch (IOException e) {
            throw new ResultException("获取HTTP输出流失败", e);
        }
    }

}
