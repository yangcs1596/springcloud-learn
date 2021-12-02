package com.safedog.cloudnet.generate.utils;

/**
 * @ClassName: JDBCUtils
 * @Description: 描述
 * @Author: xiaojl
 * @CreateDate: 2020-12-28 14:33
 * @Version: 1.0
 */

import java.sql.*;


@JDBCAnnotation(driver = "org.postgresql.Driver",
        name = "root",
        password = "123456",
        url = "jdbc:postgresql://192.168.88.114/notary-bzxt-la?characterEncoding=UTF-8")
public class JdbcUtils {
  private static String driver;
  private static String url;
  private static String user;
  private static String password;


  static {
    try {
      Class cla = JdbcUtils.class;
      JDBCAnnotation annotation = (JDBCAnnotation) cla.getAnnotation(JDBCAnnotation.class);
      driver = annotation.driver();
      user = annotation.name();
      password = annotation.password();
      url = annotation.url();
      Class.forName(driver);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {
    Connection con = null;
    try {
//          Statement createStatement()：创建一个 Statement 对象来将 SQL 语句发送到数据库。
      con = DriverManager.getConnection(url, user, password);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("创建连接失败");
    }
    return con;
  }
  public static void close(Connection con, Statement st, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {

      try {
        if (st != null) {
          st.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        try {
          if (con != null) {
            con.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }
}