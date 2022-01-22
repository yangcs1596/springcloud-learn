package com.safedog.common.excel.easyexcel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.safedog.common.excel.easyexcel.listener.ExcelListener;
import com.safedog.common.util.ActionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * easyexcel读取文件工具类
 *
 * @author wujf
 * @date Create in 2020/10/19 15:23
 */
public class EasyExcelUtils {
    /**
     * 读取Excel（多个sheet可以用同一个实体类解析）
     *
     * @param excelInputStream
     * @param clazz
     * @param excelListener    数据监听器
     * @return
     */
    public static List<Object> readExcel(InputStream excelInputStream, Class<? extends Object> clazz, ExcelListener excelListener) throws Exception {
        ExcelReader excelReader = EasyExcel.read(excelInputStream, clazz, excelListener).build();
        if (excelReader == null) {
            return new ArrayList();
        }
        List<ReadSheet> readSheetList = excelReader.excelExecutor().sheetList();
        for (ReadSheet readSheet : readSheetList) {
            excelReader.read(readSheet);
        }
        excelReader.finish();
        return excelListener.getDataList();
    }

    /**
     * 导出Excel(一个sheet),并放置在response的outputStream中
     *
     * @param request   HttpServletResponse
     * @param response  HttpServletResponse
     * @param list      数据list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的sheet名
     * @param clazz     实体类
     */
    public static <T> void writeExcelByStream(HttpServletRequest request, HttpServletResponse response, List<T> list, String fileName, String sheetName, Class<T> clazz) throws Exception {
        OutputStream outputStream = getOutputStream(request, response, fileName);
        ExcelWriter excelWriter = EasyExcel.write(outputStream, clazz).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        excelWriter.write(list, writeSheet);
        excelWriter.finish();
    }

    /**
     * 导出Excel(一个sheet),并生成文件
     *
     * @param list      数据list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的sheet名
     * @param clazz     实体类
     */
    public static String writeExcelByFile(List<?> list, String fileName, String sheetName, Class<T> clazz) throws Exception {
        File file = EasyExcelUtils.getExcelFile(fileName, null);
        if (file == null) {
            return null;
        }
        String filePath = file.getPath();
        ExcelWriter excelWriter = EasyExcel.write(filePath, clazz).build();
        writeExcelBySheet(list, excelWriter, sheetName, null);
        excelWriter.finish();
        return filePath;
    }

    /**
     * 写数据到sheet中
     *
     * @param list        数据list
     * @param excelWriter excel的输出对象
     * @param sheetName   sheet名称
     * @param headRow     head开始的行
     */
    public static void writeExcelBySheet(List<?> list, ExcelWriter excelWriter, String sheetName, Integer headRow) throws Exception {
        WriteSheet writeSheet = headRow == null ? EasyExcel.writerSheet(sheetName).build() : EasyExcel.writerSheet(sheetName).relativeHeadRowIndex(headRow).build();
        excelWriter.write(list, writeSheet);
        // 写入成功后，清空list释放内存
        list.clear();
    }

    /**
     * 创建excel文件
     *
     * @param fileName 文件名
     * @param suffix   后缀
     */
    public static File getExcelFile(String fileName, String suffix) throws Exception {
        String ext = suffix;
        if (suffix == null || "".equals(suffix)) {
            ext = ".xlsx";
        }
        //创建本地文件
        String filePath = fileName + ext;
        File dbfFile = new File(filePath);
        try {
            if (!dbfFile.exists() || dbfFile.isDirectory()) {
                dbfFile.createNewFile();
            }
            return dbfFile;
        } catch (Exception e) {
            throw new Exception("创建文件失败fileName:" + fileName);
        }
    }


    /**
     * 导出时生成OutputStream
     *
     * @param fileName
     */
    private static OutputStream getOutputStream(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {
        ActionUtils.setResponseHeader(request, response, fileName, null);
        return response.getOutputStream();
    }

    /**
     * 自定义excel内容样式
     *
     * @param wrapped 是否自动换行
     * @param size    字体大小
     */
    public static WriteCellStyle customerWriteCellStyle(boolean wrapped, Short size) {

        //内容样式策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //垂直居中,水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(wrapped);
        contentWriteCellStyle.setWriteFont(customerWriteFont(size));

        return contentWriteCellStyle;

    }

    /**
     * 自定义excel字体样式
     *
     * @param size 字体大小
     */

    public static WriteFont customerWriteFont(Short size) {
        // 字体策略
        WriteFont writeFont = new WriteFont();
        // 字体大小
        writeFont.setFontHeightInPoints(size);
        return writeFont;
    }

    /**
     * 自定义excel头部策略样式
     */
    public static WriteCellStyle customerHeadWriteCellStyle() {
        //头策略使用默认
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        return headWriteCellStyle;
    }
}
