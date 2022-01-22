package com.safedog.common.excel.easyexcel.model;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.safedog.common.base.model.PageVO;
import lombok.Data;

/**
 * 导出excel的入参模板
 * ParamType 方法入参参数类型
 * ExcelModelType 待导出的表头模板类型
 *
 * @author wujf
 * @date Create in 2020/10/19 17:25
 */
@Data
public class ExportExcelModel<ParamType, ExcelModelType> {
    private String fileName;
    private String sheetName;
    private ParamType params;
    private ExcelModelType headModel;
    private PageVO pageVO;
    private SheetWriteHandler sheetWriteHandler;
    private AbstractCellStyleStrategy abstractCellStyleStrategy;
    private Integer headRow;
    /**
     * 每个sheet页默认放10w数据
     */
    private Integer sheetDataCount = 100000;
    /**
     * 最多导出50w条数据
     */
    private Integer maxTotalCount = 500000;

    /**
     * @param fileName                  文件名
     * @param sheetName                 sheet名称
     * @param params                    分页查询入参
     * @param headModel                 导入的表头模板
     * @param pageVO                    分页对象
     * @param sheetWriteHandler         自定义excel内容样式
     * @param abstractCellStyleStrategy 自定义excel单元格样式
     * @param headRow                   头部开始行
     * @param sheetDataCount            每个sheet页阈值
     * @param maxTotalCount             总数量阈值
     * @return
     */
    public ExportExcelModel(String fileName, String sheetName, ParamType params, ExcelModelType headModel, PageVO pageVO, SheetWriteHandler sheetWriteHandler, AbstractCellStyleStrategy abstractCellStyleStrategy, Integer headRow, Integer sheetDataCount, Integer maxTotalCount) {
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.params = params;
        this.headModel = headModel;
        this.pageVO = pageVO;
        this.sheetWriteHandler = sheetWriteHandler;
        this.abstractCellStyleStrategy = abstractCellStyleStrategy;
        this.headRow = headRow;
        this.sheetDataCount = sheetDataCount;
        this.maxTotalCount = maxTotalCount;
    }

    /**
     * @param fileName                  文件名
     * @param sheetName                 sheet名称
     * @param params                    分页查询入参
     * @param headModel                 导入的表头模板
     * @param pageVO                    分页对象
     * @param sheetWriteHandler         自定义excel内容样式
     * @param abstractCellStyleStrategy 自定义excel单元格样式
     * @param headRow                   头部开始行
     * @return
     */
    public ExportExcelModel(String fileName, String sheetName, ParamType params, ExcelModelType headModel, PageVO pageVO, SheetWriteHandler sheetWriteHandler, AbstractCellStyleStrategy abstractCellStyleStrategy, Integer headRow) {
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.params = params;
        this.headModel = headModel;
        this.pageVO = pageVO;
        this.sheetWriteHandler = sheetWriteHandler;
        this.abstractCellStyleStrategy = abstractCellStyleStrategy;
        this.headRow = headRow;
    }

    /**
     * @param fileName  文件名
     * @param sheetName sheet名称
     * @param params    分页查询入参
     * @param headModel 导入的表头模板
     * @param pageVO    分页对象
     * @return
     */
    public ExportExcelModel(String fileName, String sheetName, ParamType params, ExcelModelType headModel, PageVO pageVO) {
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.params = params;
        this.headModel = headModel;
        this.pageVO = pageVO;
    }

    /**
     * @param fileName       文件名
     * @param sheetName      sheet名称
     * @param params         分页查询入参
     * @param headModel      导入的表头模板
     * @param pageVO         分页对象
     * @param sheetDataCount 每个sheet页阈值
     * @param maxTotalCount  总数量阈值
     * @return
     */
    public ExportExcelModel(String fileName, String sheetName, ParamType params, ExcelModelType headModel, PageVO pageVO, Integer sheetDataCount, Integer maxTotalCount) {
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.params = params;
        this.headModel = headModel;
        this.pageVO = pageVO;
        this.sheetDataCount = sheetDataCount;
        this.maxTotalCount = maxTotalCount;
    }

    public ExportExcelModel() {

    }
}
