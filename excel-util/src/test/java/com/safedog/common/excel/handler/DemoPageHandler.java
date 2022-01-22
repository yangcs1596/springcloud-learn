package com.safedog.common.excel.handler;

import com.safedog.common.base.model.PageVO;
import com.safedog.common.excel.easyexcel.handler.ExcelPageHandler;
import com.safedog.common.excel.model.WorkLoadExcelModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回分页数据
 *
 * @author wujf
 * @date Create in 2020/10/26 16:37
 */
public class DemoPageHandler implements ExcelPageHandler<WorkLoadExcelModel> {
    @Override
    public void pageData(WorkLoadExcelModel workLoadExcelModel, PageVO pageVO) {
        List<WorkLoadExcelModel> workLoadExcelModelList = new ArrayList<>();
        WorkLoadExcelModel temp1 = new WorkLoadExcelModel();
        temp1.setWorkLoadNo(1);
        temp1.setIpAddress("127.0.0.1");
        temp1.setMacCode("E6C806FD2B26589C92E0B384E0874269");
        temp1.setWorkGroupName("test1");
        temp1.setSiteTag("厦门");
        temp1.setAppTag("测试");
        temp1.setEnvTag("DMZ区");
        temp1.setRoleTag("test1");
        workLoadExcelModelList.add(temp1);
        WorkLoadExcelModel temp2 = new WorkLoadExcelModel();
        temp2.setWorkLoadNo(2);
        temp2.setIpAddress("127.0.0.2");
        temp2.setMacCode("82B2ABEDE9FCE8EB13A6BF28A209F439");
        temp2.setWorkGroupName("test2");
        temp2.setSiteTag("厦门");
        temp2.setAppTag("测试");
        temp2.setEnvTag("非DMZ区");
        temp2.setRoleTag("test2");
        workLoadExcelModelList.add(temp2);
        WorkLoadExcelModel temp3 = new WorkLoadExcelModel();
        temp3.setWorkLoadNo(3);
        temp3.setIpAddress("127.0.0.3");
        temp3.setMacCode("82B2ABEDE9FCE8EB13A6BF28A209F439");
        temp3.setWorkGroupName("test3");
        temp3.setSiteTag("厦门");
        temp3.setAppTag("测试");
        temp3.setEnvTag("即是非DMZ区也是DMZ区");
        temp3.setRoleTag("test3");
        workLoadExcelModelList.add(temp3);
        pageVO.setData(workLoadExcelModelList);
        pageVO.setTotalData(3);
    }
}
