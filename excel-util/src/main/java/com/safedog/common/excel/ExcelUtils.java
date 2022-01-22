package com.safedog.common.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.safedog.common.base.context.PageContext;
import com.safedog.common.base.model.PageVO;
import com.safedog.common.excel.easyexcel.handler.ExcelPageHandler;
import com.safedog.common.excel.easyexcel.listener.ExcelListener;
import com.safedog.common.excel.easyexcel.model.ExportExcelModel;
import com.safedog.common.excel.easyexcel.model.ImportExcelModel;
import com.safedog.common.excel.easyexcel.util.EasyExcelUtils;
import com.safedog.common.exception.ErrorCodeEnum;
import com.safedog.common.thread.ThreadPoolManager;
import com.safedog.common.util.ActionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

/**
 * excel导入导出的工具类
 *
 * @author wujf
 * @date Create in 2020/10/19 15:20
 */
@Slf4j
public class ExcelUtils {
    private final static Object OBJECT = new Object();

    /**
     * 读取Excel（多个sheet可以用同一个实体类解析）
     *
     * @param importExcelModel 导入excel的实体
     * @param excelListener    导入数据监听基类
     * @return
     */

    public static List<Object> readExcelByFile(ImportExcelModel importExcelModel, ExcelListener excelListener) throws Exception {
        if (importExcelModel == null || importExcelModel.getInputStream() == null || StringUtils.isBlank(importExcelModel.getFileName()) || excelListener == null) {
            log.error("导入excel时，文件名或者文件流为空！");
            throw new Exception("参数为空！");
        }
        boolean b = importExcelModel.getFileName() == null ||
                (!importExcelModel.getFileName().toLowerCase().endsWith(".xls") && !importExcelModel.getFileName().toLowerCase().endsWith(".xlsx"));
        if (b) {
            log.error("导入的文件不是标准的excel文件！");
            throw new Exception("文件格式错误！");
        }
        return EasyExcelUtils.readExcel(importExcelModel.getInputStream(), importExcelModel.getHeadModel().getClass(), excelListener);
    }

    /**
     * 多线程导出Excel,并生成本地文件
     *
     * @param exportExcelModel 导出的入参实体
     * @param excelPageHandler 分页数据处理器
     */
    public static String exportExcelByFile(ExportExcelModel exportExcelModel, ExcelPageHandler excelPageHandler) throws Exception {
        if (null == exportExcelModel || StringUtils.isBlank(exportExcelModel.getFileName()) || StringUtils.isBlank(exportExcelModel.getSheetName())) {
            log.error("导出excel时，文件名或者sheet名为空！");
            throw new Exception(ErrorCodeEnum.PARAMS_ERROR.getErrorCode());
        }
        File file = null;
        String filePath = null;
        // 直接查第一页
        PageVO pageVO = exportExcelModel.getPageVO();
        if (pageVO == null) {
            //如果pageVO为空，则创建一个
            pageVO = new PageVO();
            pageVO.setPageNo(1);
            pageVO.setPageSize(exportExcelModel.getSheetDataCount());
            pageVO.setTotalData(0);
            exportExcelModel.setPageVO(pageVO);
            PageContext.setPage(pageVO);
        }
        if (pageVO == null) {
            log.warn("获取的分页数据为空，不进行导出！");
            return null;
        }
        if (exportExcelModel.getMaxTotalCount() != null && exportExcelModel.getMaxTotalCount() < pageVO.getTotalData()) {
            log.error("导出的总条数超过阈值：{}", exportExcelModel.getMaxTotalCount());
            throw new Exception(ErrorCodeEnum.CRISIS_ERROR.getErrorCode());
        }
        // 生成本地文件
        file = EasyExcelUtils.getExcelFile(exportExcelModel.getFileName(), null);
        if (file == null) {
            log.error("创建文件失败！");
            throw new Exception("创建文件失败！");
        }
        filePath = file.getPath();
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(filePath, exportExcelModel.getHeadModel().getClass());
        // 自定义excel内容样式
        if (exportExcelModel.getSheetWriteHandler() != null) {
            excelWriterBuilder.registerWriteHandler(exportExcelModel.getSheetWriteHandler());
        }
        // 自定义excel单元格样式
        if (exportExcelModel.getAbstractCellStyleStrategy() != null) {
            excelWriterBuilder.registerWriteHandler(exportExcelModel.getAbstractCellStyleStrategy());
        }

        ExcelWriter excelWriter = excelWriterBuilder.build();
        Integer sheetNum = getTotalSheetNum(pageVO.getTotalData(), exportExcelModel.getSheetDataCount());
        if (sheetNum == 1) {
            log.warn("业务数据没有达到阈值只需导出一个sheet页");
            excelPageHandler.pageData(exportExcelModel.getParams(), pageVO);
            if (pageVO == null || pageVO.getTotalData() == 0) {
                log.warn("获取的分页数据为空，不进行导出！");
                throw new Exception(ErrorCodeEnum.DATA_ERROR.getErrorCode());
            }
            // 把数据直接写入excel的第一个sheet页(一般情况不会用到多个sheet页)
            EasyExcelUtils.writeExcelBySheet(pageVO.getData(), excelWriter, exportExcelModel.getSheetName() + 1, exportExcelModel.getHeadRow());
            excelWriter.finish();
            return filePath;
        }
        // 一个sheet无法接收所有业务数据，从第一页到最后一页多线程写入
        CountDownLatch countDownLatch = new CountDownLatch(sheetNum);
        // 多线程写入数据到不同sheet中
        exportExcelByThread(exportExcelModel, excelPageHandler, excelWriter, countDownLatch, sheetNum);
        // 等待所有线程执行完毕
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("create exportExcelByFile error：{}", e);
        } finally {
            // 清除临时文件，完成excel导出
            excelWriter.finish();
        }
        return filePath;
    }

    /**
     * 多线程导出Excel,并放置在网络流中
     *
     * @param request          请求的servlet
     * @param response         相应的servlet
     * @param exportExcelModel 导出的入参实体
     * @param excelPageHandler 分页数据处理器
     */
    public static void exportExcelByStream(HttpServletRequest request, HttpServletResponse response, ExportExcelModel exportExcelModel, ExcelPageHandler excelPageHandler) throws Exception {
        if (null == exportExcelModel || StringUtils.isBlank(exportExcelModel.getFileName()) || StringUtils.isBlank(exportExcelModel.getSheetName())) {
            log.error("导出excel时，文件名或者sheet名为空！");
            throw new Exception(ErrorCodeEnum.PARAMS_ERROR.getErrorCode());
        }
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ActionUtils.setResponseHeader(request, response, exportExcelModel.getFileName(), null);
            ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(outputStream);
            // 自定义excel内容样式
            if (exportExcelModel.getSheetWriteHandler() != null) {
                excelWriterBuilder.registerWriteHandler(exportExcelModel.getSheetWriteHandler());
            }
            // 自定义excel单元格样式
            if (exportExcelModel.getAbstractCellStyleStrategy() != null) {
                excelWriterBuilder.registerWriteHandler(exportExcelModel.getAbstractCellStyleStrategy());
            }
            ExcelWriter excelWriter = excelWriterBuilder.head(exportExcelModel.getHeadModel().getClass()).useDefaultStyle(true).excelType(ExcelTypeEnum.XLSX).build();
            //直接查第一页
            PageVO newPageVO = exportExcelModel.getPageVO();
            if (newPageVO == null) {
                //如果pageVO为空，则创建一个
                newPageVO = new PageVO();
                newPageVO.setPageNo(1);
                newPageVO.setPageSize(exportExcelModel.getSheetDataCount());
                exportExcelModel.setPageVO(newPageVO);
                PageContext.setPage(newPageVO);
            }
            if (newPageVO == null) {
                log.warn("获取的分页数据为空，不进行导出！");
                return;
            }
            if (exportExcelModel.getMaxTotalCount() != null && exportExcelModel.getMaxTotalCount() < newPageVO.getTotalData()) {
                log.error("导出的总条数超过阈值：{}！", exportExcelModel.getMaxTotalCount());
                throw new Exception(ErrorCodeEnum.CRISIS_ERROR.getErrorCode());
            }
            Integer sheetNum = getTotalSheetNum(newPageVO.getTotalData(), exportExcelModel.getSheetDataCount());
            if (sheetNum == 1) {
                log.warn("业务数据没有达到阈值只需导出一个sheet页");
                excelPageHandler.pageData(exportExcelModel.getParams(), newPageVO);
                if (newPageVO == null || newPageVO.getTotalData() == 0) {
                    log.warn("获取的分页数据为空，不进行导出！");
                    throw new Exception(ErrorCodeEnum.DATA_ERROR.getErrorCode());
                }
                // 把数据写入excel的第一个sheet页(一般情况不会用到多个sheet页)
                EasyExcelUtils.writeExcelBySheet(newPageVO.getData(), excelWriter, exportExcelModel.getSheetName() + 1, exportExcelModel.getHeadRow());
                excelWriter.finish();
                outputStream.flush();
                return;
            }
            // 一个sheet无法接收所有业务数据，从第第一页到最后一页多线程写入
            CountDownLatch countDownLatch = new CountDownLatch(sheetNum);
            // 多线程写入数据到不同sheet中
            exportExcelByThread(exportExcelModel, excelPageHandler, excelWriter, countDownLatch, sheetNum);
            // 等待所有线程执行完毕
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("create exportExcelByStream error：{}", e);
            } finally {
                // 清除临时文件，完成excel导出
                excelWriter.finish();
                outputStream.flush();
            }
        } catch (Exception e) {
            log.error("ExcelUtil exportExcelByStream is exception:{}", e);
            throw new Exception(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("ExcelUtil exportExcelByStream close outputStream error:{}", e);
                }
            }
        }
        return;
    }

    /**
     * 删除本地文件
     *
     * @param filePath
     * @return boolean
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    /**
     * 获取导出后总的sheet页数
     *
     * @param totalData      待导出的总条数
     * @param sheetDataCount 每个sheet的总条数
     * @return java.lang.Integer
     */
    private static Integer getTotalSheetNum(Integer totalData, Integer sheetDataCount) {
        // 总页数
        int totalPage = 0;
        Double totalPageTemp = Math.ceil(Double.valueOf(totalData.toString()) / sheetDataCount);
        if (totalPageTemp == 0.0) {
            totalPage++;
        } else {
            totalPage = totalPageTemp.intValue();
        }
        return totalPage;
    }

    /**
     * 多线程导出excel数据
     *
     * @param exportExcelModel 导出的入参实体
     * @param excelPageHandler 数据处理器
     * @param excelWriter      excel写入句柄
     * @param countDownLatch   线程计数器
     * @param sheetNum         总sheet数
     * @return void
     */
    private static void exportExcelByThread(ExportExcelModel exportExcelModel, ExcelPageHandler excelPageHandler, ExcelWriter excelWriter, CountDownLatch countDownLatch, Integer sheetNum) {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolManager.getThreadPool();
        IntStream.range(1, sheetNum + 1).forEach(pageNo -> {
            threadPoolExecutor.execute(() -> {
                // 设置sheet数据
                try {
                    PageVO pageVO = exportExcelModel.getPageVO();
                    if (pageVO == null) {
                        //如果pageVO为空，则创建一个
                        pageVO = new PageVO();
                        pageVO.init();
                    }
                    pageVO.setPageNo(pageNo);
                    // 重新生成一个临时对象，防止线程副本串掉
                    PageVO newPageVo = new PageVO(pageVO.getPageSize(),pageVO.getPageNo());
                    PageContext.setPage(newPageVo);
                    // 获取业务数据
                    excelPageHandler.pageData(exportExcelModel.getParams(), newPageVo);
                    synchronized (OBJECT) {
                        EasyExcelUtils.writeExcelBySheet(newPageVo.getData(), excelWriter, exportExcelModel.getSheetName() + pageNo, exportExcelModel.getHeadRow());
                    }
                } catch (Exception e) {
                    log.error("写入sheet页数据异常:{}", e);
                } finally {
                    // 计数器减一
                    countDownLatch.countDown();
                }
            });
        });
    }
}
