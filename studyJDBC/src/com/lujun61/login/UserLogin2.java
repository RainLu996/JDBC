package com.lujun61.login;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
怎么避免SQL注入？
    产生SQL注入的根本原因是：先进行字符串（sql语句）的拼接，再进行sql语句的编译。

    java.sql.Statement接口的特点：先进行字符串（sql语句）的拼接，再进行sql语句的编译
        优点：使用Statement可以进行sql语句的拼接。
        缺点：因为拼接的存在，导致可能给了不法分子机会，导致SQL注入

    java.sql.PreparedStatement接口的特点是：先进行SQL语句的编译，再进行sql语句的传值。
        优点：避免SQL注入。
        缺点：没有办法进行sql语句的拼接，只能给编译之后的sql语句进行赋值。

        用法建议：
            要 拼接sql语句/拼接字符串 的时候用Statement。
            要传值与sql中的字段值进行比较时，用PreparedStatement。
 */
public class UserLogin2 {
    public static void main(String[] args) {
        //初始化一个界面，可以让用户输入用户名和密码
        Map<String, String> loginInfo = initUI();

        //连接数据库验证用户名、密码是否正确
        boolean matching = checkNameAndPwd(loginInfo.get("name"), loginInfo.get("password"));
        System.out.println(matching ? "登陆成功！欢迎！" : "登录失败！");
    }

    private static boolean checkNameAndPwd(String name, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");

            //2、获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lujun61", "root", "123456");

            //3、获取预编译的数据库操作对象
            /* 一个 ? 是一个占位符，一个占位符只能接收一个“值/数据” */
            String sql = "select * from t_login where login_name = ? and login_password = ?";
            ps = conn.prepareStatement(sql);//会将sql语句交给DBMS处理

            //给占位符传值（JDBC中所有的下标皆从1开始）
            ps.setString(1, name);//1代表第一个问号
            ps.setString(2, password);


            //4、执行SQL语句
            rs = ps.executeQuery();

            //铁定只有一条值
            if (rs.next()) {
                return true;
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
        return false;
    }

    private static Map<String, String> initUI() {
        System.out.println("欢迎使用该系统！请输入用户名与密码进行身份验证！");
        Scanner s = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String name = s.nextLine();//接受用户输入，一次接受一行
        System.out.print("请输入密码：");
        String password = s.nextLine();

        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("password", password);
        return map;
    }
}
