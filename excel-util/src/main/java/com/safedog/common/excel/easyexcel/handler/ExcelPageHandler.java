package com.safedog.common.excel.easyexcel.handler;

import com.safedog.common.base.model.PageVO;

/**
 * excel页数据处理器
 *
 * @author wujf
 * @date Create in 2020/10/20 15:23
 */
@FunctionalInterface
public interface ExcelPageHandler<T> {
    /**
     * 获取分页的业务数据
     *
     * @param t      分页入参实体
     * @param pageVO 分页参数
     * @return PageVO 返回的分页实体
     */
    void pageData(T t, PageVO pageVO);
}
