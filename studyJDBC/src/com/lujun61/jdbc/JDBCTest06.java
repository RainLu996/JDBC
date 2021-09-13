package com.lujun61.jdbc;

import com.lujun61.jdbc.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*JDBC默认情况下对事务怎样进行处理？
    自动提交！！！ 即：只要执行一条DML语句就自动提交一次

    在实际开发中，必须将自动提交机制关闭掉！改成手动提交。
    当一个完整的事务结束之后再手动提交！！！

    conn.setAutoCommit(false);//关闭自动提交机制
    conn.commit();//手动提交
 */


public class JDBCTest06 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            //1、注册驱动   2、获取数据库连接
            conn = JDBCUtil.getConnection();

            //开启事务，将自动提交机制关闭掉
            conn.setAutoCommit(false);

            //3、获取预编译的数据库操作对象
            String sql = "update t_act set balance = ? where actno = ?";
            ps = conn.prepareStatement(sql);
            //给?赋值
            ps.setInt(1, 10000);
            ps.setString(2, "A");
            count = ps.executeUpdate();//更新成功一条记录，则返回1

            //模拟异常
            String s = null;
            s.toString();

            ps.setInt(1, 10000);
            ps.setString(2, "B");
            count += ps.executeUpdate();

            System.out.println(count == 2 ? "转账成功！" : "转账失败！");

            conn.commit();//手动提交
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            //出现异常，就回滚
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            JDBCUtil.close(conn, ps, null);
        }
    }
}
