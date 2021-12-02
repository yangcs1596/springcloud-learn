package com.safedog.cloudnet.generate.processor.base;

import com.google.common.base.Strings;
import com.safedog.cloudnet.generate.utils.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.*;
import java.util.*;

/**
 * @author ycs
 * @description 基础属性
 * @date 2021/11/8 20:49
 */
@Data
public abstract class BaseProcessor {
    @ApiModelProperty(value = "模板名")
    private String ftlName;
    @ApiModelProperty(value = "输出路径")
    private String outputPath;
    @ApiModelProperty(value = "输出文件名称")
    private String outputFileName;
    @ApiModelProperty(value = "表名")
    private String tableName;
    @ApiModelProperty(value = "包名")
    private String packageName = "";
    @ApiModelProperty(value = "前缀")
    private String tablePrefix;
    @ApiModelProperty(value = "模块名称")
    private String cloudName;
    @ApiModelProperty(value = "数据")
    private Map<String, Object> dataMap = new HashMap<String, Object>();

    public BaseProcessor(String tableName, String tablePrefix, String ftlName) {
        this.tableName = tableName;
        this.ftlName = ftlName;
        this.tablePrefix = tablePrefix;
//        iniFtlName();
    }

    public BaseProcessor(String tableName, String tablePrefix) {
        this.tableName = tableName;
        this.tablePrefix = tablePrefix;
//        iniFtlName();
        initPathName();
    }

    protected void initPathName(){

    }

    private void bindDefaultData() {
        TableDesc tableDesc = getTableDesc(getTableName());
        String tableIdName = StringKit.deCodeUnderlined(tableDesc.getIdName());

        getDataMap().put("attrs", getColumnAttrs());
        getDataMap().put("tableNameFL", tableDesc.getHumpName());
        getDataMap().put("tableNameFU", tableDesc.getHumpNameFU());
        getDataMap().put("tableIdName", tableIdName);
        if (!Strings.isNullOrEmpty(PropertiesUtil.getValue(Constant.TABLE_PREFIX))) {
            String tablePreFix = PropertiesUtil.getValue(Constant.TABLE_PREFIX);
            String tableHumpName = tableDesc.getHumpName();

            String tableNameShort = tableHumpName.substring(tableHumpName.toUpperCase().startsWith(tablePreFix.toUpperCase()) ? tablePreFix.length() : 0);
            String tableNameShortFL = StringKit.toLowerCaseFirstOne(tableNameShort);
            String tableNameShortFU = StringKit.toUpperCaseFirstOne(tableNameShort);
            getDataMap().put("tableNameShortFL", tableNameShortFL);
            getDataMap().put("tableNameShortFU", tableNameShortFU);
        }
    }

    public void bindData() {
        TableDesc tableDesc = getTableDesc(getTableName());
        String tableIdName = StringKit.deCodeUnderlined(tableDesc.getIdName());
        getDataMap().put("tableName", getTableName());
        getDataMap().put("attrs", getColumnAttrs());
        getDataMap().put("tableNameFL", getTableNameFL());
        getDataMap().put("tableNameFU", getTableNameFU());
        getDataMap().put("tableIdName", tableIdName);
        getDataMap().put("packageName", PropertiesUtil.getValue(Constant.BASE_PACKAGE_NAME));
    }

    /**
     * 首字母小写的表名
     * @return
     */
    public String getTableNameFL() {
        String prefix = "tb";
        if (StringUtils.isNotEmpty(this.tablePrefix)) {
            prefix = StringKit.deCodeUnderlined(this.tablePrefix);
        }
        TableDesc tableDesc = getTableDesc(getTableName());
        String tableNameFL = StringKit.deCodeUnderlined(tableDesc.getMetaName());

        tableNameFL = tableNameFL.substring((tableNameFL.startsWith(prefix) || tableNameFL.startsWith(prefix.toUpperCase())) ? prefix.length() : 0);
        tableNameFL = StringKit.toLowerCaseFirstOne(tableNameFL);//首字母小写
        return tableNameFL;
    }

    /**
     * 根据表名，获取首字母大写
     * @return
     */
    public String getTableNameFU() {
        return StringKit.toUpperCaseFirstOne(getTableNameFL());
    }


    /**
     * 执行生成过程
     *
     * @throws Exception
     */
    public void process() throws Exception {
        this.bindDefaultData();
        this.bindData();
        File file = new File(outputPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        Writer w = new OutputStreamWriter(new FileOutputStream(outputPath + "/" + outputFileName));
        if (!Strings.isNullOrEmpty(this.ftlName)) {
            GeratorManger.getInstance().process(this.ftlName, this.dataMap, w);
        }
    }

    /**
     * 获取表信息
     *
     * @param tableName
     * @return
     */
    public TableDesc getTableDesc(String tableName) {
        TableDesc tableDesc = new TableDesc(tableName, "");
        try {
            // 加载MySql的驱动类
            Class.forName(PropertiesUtil.getValue(Constant.DRIVER_CLASS));
            String url = PropertiesUtil.getValue(Constant.JDBC_URL);
            String username = PropertiesUtil.getValue(Constant.JDBC_USERNAME);
            String password = PropertiesUtil.getValue(Constant.JDBC_PASSWORD);
            Connection conn = null;
            ResultSet rs = null;
            try {
                conn = DriverManager.getConnection(url, username, password);
                DatabaseMetaData dbmd = conn.getMetaData();
                rs = dbmd.getPrimaryKeys(null, null, tableDesc.getMetaName());

                int count = 0;
                while (rs.next()) {
                    count++;
                    String metaIdName = rs.getString("COLUMN_NAME");

                    tableDesc.setIdName(metaIdName);
                }
                rs.close();

                rs = dbmd.getColumns(null, "%", tableDesc.getMetaName(), "%");
                while (rs.next()) {
                    String colName = rs.getString("COLUMN_NAME");
                    int sqlType = rs.getInt("DATA_TYPE");
                    Integer size = rs.getInt("COLUMN_SIZE");
                    Object o = rs.getObject("DECIMAL_DIGITS");
                    Integer digit = null;
                    if (o != null) {
                        digit = ((Number) o).intValue();
                    }

                    String remark = rs.getString("REMARKS");
                    ColDesc col = new ColDesc(colName, sqlType, size, digit, remark);
                    tableDesc.addCols(col);
                }
                rs.close();
                return tableDesc;
            } catch (SQLException se) {
                System.out.println("数据库连接失败！");
                se.printStackTrace();
                return null;
            } finally {
                if (null != rs) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (SQLException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驱动程序类 ，加载驱动失败！");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取列信息
     *
     * @return
     */
    public List<Map> getColumnAttrs() {
        final TableDesc tableDesc = getTableDesc(this.tableName);
        String className = tableDesc.getMetaName();
        String ext = null;

        Set<String> cols = tableDesc.getMetaCols();
        List<Map> attrs = new ArrayList<Map>();
        for (String col : cols) {

            ColDesc desc = tableDesc.getColDesc(col);
            if (FieldsExclude.CLOUD_EXCLUDE.contains(desc.colName.toLowerCase())) {
                continue;
            }
            Map<String, Object> attr = new HashMap<>();
            if (desc.remark.contains("{")) {
                attr.put("comment", desc.remark.substring(0, desc.remark.indexOf("{")));
            } else {
                attr.put("comment", desc.remark);
            }

            attr.put("name", desc.colName);
            attr.put("humpName", StringKit.deCodeUnderlined(desc.colName));
            attr.put("humpNameFU", StringKit.toUpperCaseFirstOne(attr.get("humpName").toString()));
            attr.put("type", desc.remark);
            String type;
            if (2001 == desc.sqlType) {
                type = "String";
            } else {
                type = JavaType.getType(desc.sqlType, desc.size, desc.digit);
            }
            if ("Timestamp".equalsIgnoreCase(type)) {
                type = "Date";
            } else if ("Integer".equalsIgnoreCase(type)) {
                if (desc.size == 1) {
                    type = "boolean";
                }
            }
            attr.put("type", type);
            String frontendType = type;
            if ("String".equals(type)) {
                frontendType = "string";
            } else if ("Integer".equals(type) || "int".equals(type) || "long".equals(type) || "Long".equals(type)) {
                frontendType = "number";
            } else if ("Boolean".equals(type)) {
                frontendType = "boolean";
            }
            attr.put("frontendType", frontendType);
            attr.put("desc", desc);
            if (!tableDesc.getIdName().equalsIgnoreCase(desc.colName)) {
                attrs.add(attr);
            }
        }

        // 主键总是拍在前面，int类型也排在前面，剩下的按照字母顺序排
        Collections.sort(attrs, new Comparator<Map>() {

            @Override
            public int compare(Map o1, Map o2) {
                ColDesc desc1 = (ColDesc) o1.get("desc");
                ColDesc desc2 = (ColDesc) o2.get("desc");
                int score1 = score(desc1);
                int score2 = score(desc2);
                if (score1 == score2) {
                    return desc1.colName.compareTo(desc2.colName);
                } else {
                    return score2 - score1;
                }

            }

            private int score(ColDesc desc) {
                if (tableDesc.getMetaIdName() != null && tableDesc.getMetaIdName().equalsIgnoreCase(desc.colName)) {
                    return 99;
                } else if (JavaType.isInteger(desc.sqlType)) {
                    return 9;
                } else if (JavaType.isDateType(desc.sqlType)) {
                    return -9;
                } else {
                    return 0;
                }
            }

        });
        return attrs;
    }

}
