package com.safedog.cloudnet;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
class HdfsTest {

    @Before
    public void init(){
        System.setProperty("hadoop.home.dir", "D:\\hadoop\\hadoop-2.7.0\\bin");
    }

    @Test
    void contextLoads() {
        System.setProperty("hadoop.home.dir", "D:\\hadoop\\hadoop-2.7.0");
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://192.168.88.89:9000");
        configuration.set("hadoop.home.dir", "D:\\hadoop\\hadoop-2.7.0");
        try {
            FileSystem fs = FileSystem.get(configuration);
//            fs.copyFromLocalFile(new Path("D:\\测试文档\\d.txt"), new Path("/test"));
            fs.copyToLocalFile(new Path("hdfs://192.168.88.89:9000/cc.txt"), new Path("C:\\Users\\ASUS\\Desktop\\d.txt"));
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private FileSystem fileSystem;

    @Value("${hadoop.namespace:/}")
    private String nameSpace;


    @Test
    public void filesInfo() throws IOException {
        System.setProperty("hadoop.home.dir", "D:\\hadoop\\hadoop-2.7.0");
        RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(new Path(nameSpace), true);
        while (files.hasNext()){
            LocatedFileStatus fileStatus = files.next();
            log.info("名字: "+fileStatus.getPath().getName());
            log.info("文件分组: "+fileStatus.getGroup());
            log.info("文件长度: "+String.valueOf(fileStatus.getLen()));
            log.info("文件权限: "+String.valueOf(fileStatus.getPermission()));
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            log.info("block 数："+blockLocations.length);
            for (BlockLocation blockLocation : blockLocations) {
                String[] hosts = blockLocation.getHosts();
                for (String host : hosts) {
                    System.out.println(fileStatus.getPath().getName()+"block主机节点："+host);
                }
            }
        }
    }


    @Test
    public void isFile() throws IOException {
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path(nameSpace));
        for (FileStatus fileStatus : fileStatuses) {
            if (fileStatus.isFile()){
                log.info("文件："+fileStatus.getPath().getName());
            }
        }
    }
}

