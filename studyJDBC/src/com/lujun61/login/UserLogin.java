package com.lujun61.login;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
实现一个登录功能。

	第一步：提供一个输入的界面，可以让用户输入用户名和密码。

	第二步：底层数据库当中需要有一张用户表，用户表中存储了用户信息。

	第三步：当java程序接收到用户名和密码的时候，连接数据库验证用户名和密码。
	验证通过，表示登录成功，验证失败，表示登录失败。
 */

/*SQL注入现象

用户名：k
密码：k' or '1'='1     ————————>恶意扭曲SQL语句语义
登陆成功！


造成此现象的根本原因：
    用户提供的信息参与了底层SQL语句的编译。
 */


public class UserLogin {
    public static void main(String[] args) {
        //初始化一个界面，可以让用户输入用户名和密码
        Map<String, String> loginInfo = initUI();

        //连接数据库验证用户名、密码是否正确
        boolean matching = checkNameAndPwd(loginInfo.get("name"), loginInfo.get("password"));
        System.out.println(matching ? "登陆成功！欢迎！" : "登录失败！");
    }

    /**
     * 连接数据库验证用户名、密码是否正确
     * @param name 用户名
     * @param password 密码
     * @return true表示登陆成功，false表示登录失败
     */
    private static boolean checkNameAndPwd(String name, String password) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            //1、注册驱动
            Class.forName("com.mysql.jdbc.Driver");

            //2、获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lujun61", "root", "123456");

            //3、通过数据库连接对象创建数据库操作对象
            st = conn.createStatement();

            //4、通过数据库操作对象来执行SQL语句，操作数据库
            String sql = "select * from t_login where login_name = '" + name + "' and login_password = '" + password + "'";
            rs = st.executeQuery(sql);
            System.out.println(sql);
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
        return false;
    }

    /**
     * 初始化用户界面
     * @return 返回值是一个map集合，里面存放用户登录信息
     */
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
