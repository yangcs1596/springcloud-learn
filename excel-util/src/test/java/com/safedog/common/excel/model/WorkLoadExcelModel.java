package com.safedog.common.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * 工作负载导入导出的类模板
 *
 * @author wujf
 * @date Create in 2020/10/14 19:51
 */
@Data
@ColumnWidth(35)
public class WorkLoadExcelModel {

    @ExcelProperty(value = "序号", index = 0)
    private Integer workLoadNo;

    @ExcelProperty(value = "工作负载IP", index = 1)
    private String ipAddress;

    @ExcelProperty(value = "MacCode", index = 2)
    private String macCode;

    @ExcelProperty(value = "工作组名", index = 3)
    private String workGroupName;

    @ExcelProperty(value = "位置标签", index = 4)
    private String siteTag;

    @ExcelProperty(value = "应用标签", index = 5)
    private String appTag;

    @ExcelProperty(value = "环境标签", index = 6)
    private String envTag;

    @ExcelProperty(value = "角色标签", index = 7)
    private String roleTag;
}
