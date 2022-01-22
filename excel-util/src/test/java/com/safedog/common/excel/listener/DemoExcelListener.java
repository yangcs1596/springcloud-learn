package com.safedog.common.excel.listener;

import com.safedog.common.excel.easyexcel.listener.ExcelListener;
import com.safedog.common.excel.model.WorkLoadExcelModel;

import java.util.List;

/**
 * *
 *
 * @author wujf
 * @date Create in 2020/10/26 16:51
 */
public class DemoExcelListener extends ExcelListener {
    public DemoExcelListener(Integer maxSaveNum) {
        super(maxSaveNum);
    }

    public DemoExcelListener() {
    }

    /**
     * 根据业务自行实现该方法，例如将解析好的dataList存储到数据库中
     *
     * @param
     * @return void
     */
    public void handleBusinessLogic(List<Object> dataList) {
        for (Object object : dataList) {
            System.out.println("已入库:" + ((WorkLoadExcelModel)object).toString());
        }

        return;
    }
}
