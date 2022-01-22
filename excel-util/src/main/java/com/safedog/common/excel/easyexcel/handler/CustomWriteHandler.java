package com.safedog.common.excel.easyexcel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自定义excel导出数据的头部样式
 *
 * @author wujf
 * @date Create in 2020/10/23 13:58
 */
@Data
public class CustomWriteHandler implements SheetWriteHandler {
    /**
     * 行的起点标识（合并行时）
     */
    public final static String FIRST_ROW = "firstRow";
    /**
     * 行的终点标识（合并行时）
     */
    public final static String LAST_ROW = "lastRow";
    /**
     * 列的起点标识（合并列时）
     */
    public final static String FIRST_COL = "firstCol";
    /**
     * 列的终点标识（合并列时）
     */
    public final static String LAST_COL = "lastCol";
    /**
     * 合并行或者列的标识
     */
    public final static String CELL_RANGE_KEY = "cellRange";
    private Map<Object, Map<String, Object>> excelStyleMap;

    public CustomWriteHandler(Map<Object, Map<String, Object>> excelStyleMap) {
        this.excelStyleMap = excelStyleMap;
    }


    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        //获取每个sheet
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if(sheet.getLastRowNum() > 0){
                continue;
            }
            //创建行
            Row row = sheet.createRow(0);
            //创建单元格
            Cell cell = row.createCell(0);
            if (MapUtils.isNotEmpty(this.excelStyleMap)) {
                // 获取单元格合并参数集合
                Map<String, Object> cellRangeMap = this.excelStyleMap.get(CELL_RANGE_KEY);
                if (MapUtils.isNotEmpty(cellRangeMap)) {
                    // 设置合并范围
                    sheet.addMergedRegionUnsafe(new CellRangeAddress(Integer.valueOf(cellRangeMap.get(FIRST_ROW).toString()), Integer.valueOf(cellRangeMap.get(LAST_ROW).toString()), Integer.valueOf(cellRangeMap.get(FIRST_COL).toString()), Integer.valueOf(cellRangeMap.get(LAST_COL).toString())));
                }
                // 按照key进行排序 忽略合并标识(不忽略多个sheet会导致样式失效)
                Map<Object, Map<String, Object>> newExcelStyleMap = excelStyleMap.entrySet().stream().filter(a -> !a.getKey().equals(CELL_RANGE_KEY)).sorted(Comparator.comparing(e -> Integer.valueOf(e.getKey().toString()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

                if (MapUtils.isNotEmpty(newExcelStyleMap)) {
                    StringBuffer cellValueStr = new StringBuffer();
                    Integer cellValueRowNum = 0;
                    for (Map.Entry<Object, Map<String, Object>> entry : newExcelStyleMap.entrySet()) {
                        for (Map.Entry<String, Object> entryValue : entry.getValue().entrySet()) {
                            cellValueStr.append(entryValue.getKey()).append("：").append(entryValue.getValue()).append("    ");
                        }
                        cellValueRowNum++;
                        if (cellValueRowNum != newExcelStyleMap.size()) {
                            cellValueStr.append("\n");
                        }
                    }
                    cell.setCellValue(cellValueStr.toString());
                }
            }
            // 设置样式
            CellStyle cellStyle = workbook.createCellStyle();
            // 垂直居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            // 水平居中
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            // 设置单元格背景色
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setWrapText(true);

            // 字体样式
            Font font = workbook.createFont();
            font.setFontHeight((short) 250);
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
        }
    }

}