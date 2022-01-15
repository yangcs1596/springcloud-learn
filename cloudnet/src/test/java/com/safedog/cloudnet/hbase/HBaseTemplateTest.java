package com.safedog.cloudnet.hbase;

import com.safedog.cloudnet.entity.phoenix.SysUser;
import com.safedog.cloudnet.mapper.phoenix.TestMapper;
import com.safedog.cloudnet.phoenix.HBaseService;
import com.safedog.cloudnet.service.SysUserService;
import com.safedog.cloudnet.template.HBaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class HBaseTemplateTest {

    @Autowired
    private HBaseTemplate hBaseTemplate;

    @Autowired
    private HBaseService hBaseService;
    @Autowired
    private SysUserService sysUserService;

    @Resource
    private TestMapper testMapper;

    @Autowired
    @Qualifier("phoenixSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @Test
    public void test() {
        ArrayList<String> strings = new ArrayList<>(3);
        strings.add("name");
        strings.add("sex");
        strings.add("phone");
        List<String> strings1 = hBaseTemplate.listTableNames();
        System.out.println(strings1);
    }

    @Test
    public void phoenixTest() throws Exception{
//        List<Object> objects = sqlSessionTemplate.selectList("select name from patch_scan_info limit 10;");
        List<SysUser> sysUsers = testMapper.queryAll();
        System.out.println(sysUsers);
    }

    @Test
    public void mysqlTest(){
        List<com.safedog.cloudnet.entity.mysql.SysUser> sysUsers = sysUserService.testSelect();
        System.out.println(sysUsers);
    }

    @Test
    public void connectPhoenixTest() throws Exception{
        String testSQL = "select * from user";
        try {
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
        } catch (ClassNotFoundException e1) {
            System.out.println("org.apache.phoenix.jdbc.PhoenixDriver未找到");
        }
        List<String>resList = new ArrayList<>();
        Connection con1 = DriverManager.getConnection("jdbc:phoenix:hdfs1.safedog.cn:2181","","");
        Statement stmt = con1.createStatement();
        ResultSet rset = stmt.executeQuery(testSQL);
        while (rset.next()) {
            resList.add(rset.getString("name"));
        }
        System.out.println(resList.size());
        stmt.close();
        con1.close();
    }
}
