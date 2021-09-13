package com.lujun61.jdbc;

import java.sql.*;
import java.util.ResourceBundle;

public class JDBCTest03 {
    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("resources/db");
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        Statement st = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            Class.forName(driver);

            conn = DriverManager.getConnection(url, user, password);

            st = conn.createStatement();

            rs = st.executeQuery("select empno,ename from emp");

            while(rs.next()) {
                String empno = rs.getString("empno");
                String ename = rs.getString("ename");
                System.out.println(empno + "\t" + ename);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                st.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
