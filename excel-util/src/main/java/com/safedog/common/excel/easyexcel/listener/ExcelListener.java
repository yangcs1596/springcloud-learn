package com.safedog.common.excel.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * excel数据保存基类
 *
 * @author wujf
 * @date Create in 2020/10/19 16:11
 */
@Data
public class ExcelListener extends AnalysisEventListener {
    /**
     * 读取excel的条数
     */
    private List<Object> dataList = new ArrayList<>();
    /**
     * 默认放在内存中最大的条数为100000，超过直接保持到数据库
     */
    private Integer maxSaveNum = 100000;

    /**
     * 增加有参构造方法，供调用者修改阈值
     *
     * @param maxSaveNum 用户自定义阈值
     * @return
     */
    public ExcelListener(Integer maxSaveNum) {
        this.maxSaveNum = maxSaveNum;
    }

    /**
     * 保留无参构造方法
     *
     * @return
     */
    public ExcelListener() {
    }

    @Override
    public void invoke(Object object, AnalysisContext context) {
        dataList.add(object);
        // 如数据过大，可以进行定量分批处理
        if (dataList.size() > this.getMaxSaveNum()) {
            handleBusinessLogic(dataList);
            dataList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        return;
    }

    /**
     * 根据业务自行实现该方法，例如将解析好的dataList存储到数据库中
     *
     * @param
     * @return void
     */
    public void handleBusinessLogic(List<Object> dataList) {
        return;
    }
}