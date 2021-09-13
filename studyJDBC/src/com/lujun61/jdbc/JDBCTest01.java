package com.lujun61.jdbc;

import java.sql.*;

public class JDBCTest01 {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;
        try {
            //1、注册驱动
            //java.sql.Driver 是SUN公司写的接口
            //com.mysql.jdbc.Driver 是mysql公司实现的以上接口
            Driver driver = new com.mysql.jdbc.Driver();//缩略写法（只能省略一个包名）
            //java.sql.Driver driver = new com.mysql.jdbc.Driver();//完整写法
            DriverManager.registerDriver(driver);

            //一步到位！！！
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            //2、获取数据库连接（对象）
            String url = "jdbc:mysql://localhost:3306/lujun61";
            String user = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, user, password);

            //3、通过连接对象获取数据库操作对象(将SQL 陈述statement 给)
            st = conn.createStatement();

            //4、执行SQL语句
            //executeUpdate中只能传入DML语句
            //int insertRow = st.executeUpdate("insert into dept(deptno, dname, loc) values(50, 'SEVER', 'CHINA')");
            //System.out.println(insertRow);

            //int updateRow = st.executeUpdate("update dept set loc = 'TIANJIN' where deptno = 50");
            //System.out.println(updateRow);

            int deleteRow = st.executeUpdate("delete from dept where deptno = 50");
            System.out.println(deleteRow);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //6、释放所有资源
            //在公司里面，程序都是在服务器里面运行，main方法永远不会结束，这个时候，就需要手动关闭资源了！！！
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try{
                st.close();
            } catch(SQLException s) {
                s.printStackTrace();
            }
        }
    }
}
