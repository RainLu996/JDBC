package com.lujun61.jdbc;

import java.sql.*;

public class JDBCTest02 {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            //1、注册驱动（方式一）
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //1、注册驱动（方式二）
            Class.forName("com.mysql.jdbc.Driver");


            //2、获取数据库连接（创建数据库连接对象）
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lujun61", "root", "123456");

            //3、通过数据库连接对象来创建数据库操作对象
            st = conn.createStatement();

            //4、通过数据库操作对象来调用方法执行SQL语句
            /* 执行DQL语句使用executeQuery */
            rs = st.executeQuery("select deptno, ename as modify_name, sal from emp");

            /*1、统一以String类型返回
            while(rs.next()) {
                *//* 通过行数来取对象 *//*
                //getString方法不管底层数据是什么类型，都通通的将返回值转换成String类型
                String deptno = rs.getString(1);
                String ename = rs.getString(2);
                String sal = rs.getString(3);
                System.out.println(deptno + "\t" + ename + "\t" + sal);
            }
            */

            /*2、以指定类型返回
            while(rs.next()) {
                *//* 通过行数来取对象 *//*
                //getString方法不管底层数据是什么类型，都通通的将返回值转换成String类型
                int deptno = rs.getInt(1);
                String ename = rs.getString(2);
                float sal = rs.getFloat(3);
                System.out.println(deptno + "\t" + ename + "\t" + sal);
            }
            */

            //3、根据查询结果的列名取出结果————————>常用！！！更加健壮：有目的性的查找永远都是健壮的
            while(rs.next()) {
                /* 通过列名来取对象 */
                //getString方法不管底层数据是什么类型，都通通的将返回值转换成String类型
                String deptno = rs.getString("deptno");
                String ename = rs.getString("modify_name");//是查询结果中的列名，而不是原表中的列名！！！
                int sal = rs.getInt("sal");
                System.out.println(deptno + "\t" + ename + "\t" + sal);
            }


        } catch(SQLException | ClassNotFoundException s) {
            s.printStackTrace();
        } finally {
            /* 6、关闭所有资源 */
            //最先关闭孙子
            try {
                rs.close();
            } catch (SQLException s) {
                s.printStackTrace();
            }

            //先关闭子
            try {
                st.close();
            } catch(SQLException s) {
                s.printStackTrace();
            }

            //再关闭父
            try {
                conn.close();
            } catch(SQLException s) {
                s.printStackTrace();
            }


        }
    }
}
