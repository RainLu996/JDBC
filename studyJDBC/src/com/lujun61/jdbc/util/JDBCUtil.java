package com.lujun61.jdbc.util;

import java.sql.*;
import java.util.ResourceBundle;

public class JDBCUtil {
    /*工具类中的构造方法一般都是私有化的：
           构造方法私有化的目的是防止new对象；工具类中静态方法直接使用“类名.”的方式调用即可！
     */
    private JDBCUtil() {}

    private static ResourceBundle bundle = ResourceBundle.getBundle("resources/db");//静态代码执行顺序：……

    //类加载时注册驱动
    static {
        try {
            Class.forName(bundle.getString("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取数据库连接对象
    public static Connection getConnection() throws SQLException {
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    //关闭资源
    public static void close(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (st != null) {
            try {
                st.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
