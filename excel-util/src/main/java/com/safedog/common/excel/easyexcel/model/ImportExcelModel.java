package com.safedog.common.excel.easyexcel.model;

import lombok.Data;

import java.io.InputStream;

/**
 * 导出excel的入参模板
 * ExcelModelType 待导入的表头模板类型
 *
 * @author wujf
 * @date Create in 2020/10/19 17:25
 */
@Data
public class ImportExcelModel<ExcelModelType> {
    private String fileName;
    private InputStream inputStream;
    private ExcelModelType headModel;

    /**
     * @param fileName    文件名
     * @param inputStream 文件输入流
     * @param headModel   导入的表头模板
     *
     * @return
     */
    public ImportExcelModel(String fileName, InputStream inputStream, ExcelModelType headModel) {
        this.fileName = fileName;
        this.inputStream = inputStream;
        this.headModel = headModel;
    }

    public ImportExcelModel() {

    }
}
