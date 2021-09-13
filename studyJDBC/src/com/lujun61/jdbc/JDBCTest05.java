package com.lujun61.jdbc;

import java.sql.*;

public class JDBCTest05 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");

            //2、获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lujun61", "root", "123456");

            //3、获取预编译的数据库操作对象
            String sql = "select ename from t_emp where ename like ?";
            ps = conn.prepareStatement(sql);
            //给?赋值
            ps.setString(1, "%S%");

            //4、执行sql语句
            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("ename"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if (ps != null) {
                try {
                    ps.close();
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
}
