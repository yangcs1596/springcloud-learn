package com.safedog.cloudnet.template;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HBaseTemplate {

    private static Logger log = LoggerFactory.getLogger(HBaseTemplate.class);
    private Admin admin;
    private org.apache.hadoop.hbase.client.Connection connection;

    public HBaseTemplate(org.apache.hadoop.conf.Configuration configuration) {
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新建表
     * @param tableName
     * @param columnFamily
     * @return
     */
    public Boolean createTable(String tableName, List<String> columnFamily){
//        try {
//            ArrayList<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>(columnFamily.size());
//
//            ColumnFamilyDe
//            columnFamily.forEach(col -> {
//                columnFamilyDescriptors.add(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(col)).build());
//            });
//            TableDescriptor tableDes = TableDescriptorBuilder
//                    .newBuilder(TableName.valueOf(tableName))
//                    .setColumnFamilies(columnFamilyDescriptors)
//                    .build();
//            if(admin.tableExists(TableName.valueOf(tableName))){
//                log.info(tableName + "表已存在");
//                return false;
//            }else{
//                admin.createTable(tableDes);
//            }
//        } catch (IOException e) {
//            log.error("============{}", e);
//            return false;
//        } finally {
//            closeSource(admin);
//        }
        return true;
    }

    /**
     * 获取所有表
     * @return
     */
    public List<String> listTableNames(){
        List<String> tables = new ArrayList<>();
        try {
            TableName[] tableNames = admin.listTableNames();
            for (TableName tableName : tableNames) {
                tables.add(tableName.getNameAsString());
            }
        } catch (IOException e) {
            log.error("获取所有表失败======{}", e);
        } finally {
            closeSource(admin);
        }
        return tables;
    }
    /**
     * 资源关闭
     * @param admin
     */
    private void closeSource(Admin admin) {
        try {
            if(!Objects.isNull(admin)){
                admin.close();
            }
        } catch (IOException e) {
            log.error("资源关闭异常");
        }
    }
}
