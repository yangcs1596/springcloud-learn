package com.safedog.cloudnet.controller.hdfs;

import com.safedog.cloudnet.template.HadoopTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *
 * @Description: TODO
 * </p>
 * @ClassName HdfsController
 * @Author pl
 * @Date 2020/10/31
 * @Version V1.0.0
 */
@Slf4j
@RestController("fileApi")
@Api(tags = {"Api-文件系统file"},description = "文件处理")
public class HdfsController {

    @Autowired
    private HadoopTemplate hadoopTemplate;

    /**
     * 将本地文件srcFile,上传到hdfs
     * @param srcFile
     * @return
     */
    @ApiOperation(value="上传文件")
    @GetMapping("/uploadFile")
    public String upload(String srcFile){
        hadoopTemplate.uploadFile(srcFile);
        return "copy";
    }
    @ApiOperation(value="删除文件")
    @DeleteMapping("/delFile")
    public String del(@RequestParam String fileName){
        hadoopTemplate.delFile(fileName);
        return "delFile";
    }

    @ApiOperation(value="下载文件")
    @GetMapping("/download")
    public void download(@RequestParam String fileName, @RequestParam String savePath){
        hadoopTemplate.download(fileName,savePath);
    }
}
