package com.safedog.common.excel;

import com.safedog.common.excel.easyexcel.model.ExportExcelModel;
import com.safedog.common.excel.easyexcel.model.ImportExcelModel;
import com.safedog.common.excel.handler.DemoPageHandler;
import com.safedog.common.excel.listener.DemoExcelListener;
import com.safedog.common.excel.model.WorkLoadExcelModel;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class ExcelUtilsTest {

    /**
     * 导入excel文件，当数据超过阈值（默认10w条）时，则会边导入数据边入库
     *
     * @return void
     */
    @Test
    public void readExcelByFile() throws Exception {
        InputStream inputStream = new FileInputStream(new File("导出测试.xlsx"));
        ImportExcelModel<WorkLoadExcelModel> importExcelModel = new ImportExcelModel<>("导出测试.xlsx", inputStream, new WorkLoadExcelModel());
        DemoExcelListener demoExcelListener = new DemoExcelListener(1);
        ExcelUtils.readExcelByFile(importExcelModel, demoExcelListener);
        List<Object> workLoadTagModels = demoExcelListener.getDataList();
        for (Object object : workLoadTagModels) {
            System.out.println("还未入库：" + ((WorkLoadExcelModel)object).toString());
        }
    }

    /**
     * 导出excel文件，当超过阈值（10w条）则会采用多线程进行多sheet的导出。会在本地生成文件
     *
     * @return void
     */
    @Test
    public void exportExcelByFile() throws Exception {
        WorkLoadExcelModel workLoadExcelModel = new WorkLoadExcelModel();
        ExportExcelModel<WorkLoadExcelModel, WorkLoadExcelModel> exportExcelModel = new ExportExcelModel<>("导出测试", "导出测试",workLoadExcelModel, workLoadExcelModel, null);
        ExcelUtils.exportExcelByFile(exportExcelModel, new DemoPageHandler());
    }

    /**
     * 导出excel文件，当超过阈值（10w条）则会采用多线程进行多sheet的导出。不会在本地生成文件
     *
     * @return void
     */
    @Test
    public void exportExcelByStream() throws Exception  {
        WorkLoadExcelModel workLoadExcelModel = new WorkLoadExcelModel();
        ExportExcelModel<WorkLoadExcelModel, WorkLoadExcelModel> exportExcelModel = new ExportExcelModel<>("导出测试", "导出测试",workLoadExcelModel, workLoadExcelModel, null);
        ExcelUtils.exportExcelByStream(null, null, exportExcelModel, new DemoPageHandler());
    }
}