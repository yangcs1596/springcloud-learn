package com.safedog.cloudnet.controller.hdfs;

import com.safedog.cloudnet.template.HadoopTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


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
@RestController
public class HdfsController {

    @Autowired
    private HadoopTemplate hadoopTemplate;

    /**
     * 将本地文件srcFile,上传到hdfs
     * @param srcFile
     * @return
     */
    @GetMapping("/upload")
    public String upload(String srcFile){
        hadoopTemplate.uploadFile(srcFile);
        return "copy";
    }

    @DeleteMapping("/delFile")
    public String del(@RequestParam String fileName){
        hadoopTemplate.delFile(fileName);
        return "delFile";
    }

    @GetMapping("/download")
    public void download(@RequestParam String fileName, @RequestParam String savePath){

        hadoopTemplate.download(fileName,savePath);
    }
}
