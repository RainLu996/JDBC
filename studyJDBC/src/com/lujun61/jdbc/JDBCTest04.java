package com.lujun61.jdbc;

import java.sql.*;

public class JDBCTest04 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            //1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");

            //2、获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lujun61", "root", "123456");

            /*insert操作
            //3、获取预编译的数据库操作对象
            String sql = "insert into t_dept(deptno, dname, loc) values(?, ?, ?)";
            ps = conn.prepareStatement(sql);
            //给?传值
            ps.setInt(1, 50);
            ps.setString(2, "技术部门");
            ps.setString(3, "北京朝阳区");

            //4、执行sql语句
            ps.executeUpdate();
            */

            /*update操作
            //3、获取预编译的数据库操作对象
            String sql = "update t_dept set dname = ?, loc = ? where deptno = ?";
            ps = conn.prepareStatement(sql);
            //给?传值
            ps.setString(1, "数据管理部门");
            ps.setString(2, "北京");
            ps.setInt(3, 50);

            //4、执行sql语句
            ps.executeUpdate();
            */

            //delete操作
            //3、获取预编译的数据库操作对象
            String sql = "delete from t_dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            //给字符串对象赋值
            ps.setInt(1, 50);

            //4、执行sql语句
            ps.executeUpdate();
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
