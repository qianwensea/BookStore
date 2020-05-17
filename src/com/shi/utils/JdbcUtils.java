package com.shi.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 千文sea
 * @create 2020-04-19 15:47
 */
public class JdbcUtils {

    /**
    注意点:
    原来数据库5.5升级到8.0后，数据库连接配置没有改造成的

        1、驱动包要升级为 mysql-connector-java-8.0.11.jar

        https://dev.mysql.com/downloads/file/?id=477058

        2、JDBC driver 由“com.mysql.jdbc.Driver”改为“com.mysql.cj.jdbc.Driver”

        3、url中加上“userSSL=false”。否则会出现以下错误：

        “Establishing SSL connection withoutserver's identity verification is not recommended. According to MySQL 5.5.45+,5.6.26+ and 5.7.6+ requirements SSL connection must be established by defaultif explicit option isn't set. For compliance with existing applications notusing SSL the verifyServerCertificate property is set to 'false'. You needeither to explicitly disable SSL by setting useSSL=false, or set useSSL=trueand provide truststore for server certificate verification.”

        4、url中加上“serverTimezone=GMT%2B8”（GMT%2B8代表东八区）

        jdbc.driver=com.mysql.cj.jdbc.Driver
        jdbc.url=jdbc:mysql://localhost:3306/book?useUnicode=true&characterEncoding=utf-8&userSSL=false&serverTimezone=GMT%2B8
     */
    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();

    static{

        try {
            Properties properties = new Properties();
            //读取jdbc.properties属性配置文件
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //从流中加载数据
            properties.load(inputStream);
            //创建数据库连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

//            System.out.println(dataSource.getConnection());  com.mysql.cj.jdbc.ConnectionImpl@27fe3806
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接池中的连接
     * @return 如果返回null说明获取链接失败,如果有值则连接成功
     */
    public static Connection getConnection(){
        Connection conn = conns.get();
        if (conn == null){
            try {
                //如果为空,从数据库连接池中取
                conn = dataSource.getConnection();

                //将数据库连接对象保存到threadlocal对象中,共后面的jdbc操作使用
                conns.set(conn);

                //设置为手动管理
                conn.setAutoCommit(false);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 提交事物并释放连接
     */
    public static void commitAndClose(){
        Connection conn = conns.get();

        //如果不为null,说明之前使用过连接操作过数据库
        if (conn != null){
            try {
                //提交事务
                conn.commit();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                //关闭连接,释放资源
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //一定要执行remove操作,否则就会出错(因为Tomcat底层使用了线程池)
        conns.remove();
    }

    /**
     * 回滚事物并释放连接
     */
    public static void rollbackAndClose(){
        Connection conn = conns.get();

        //如果不为null,说明之前使用过连接操作过数据库
        if (conn != null){
            try {
                //回滚事务
                conn.rollback();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                //关闭连接,释放资源
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //一定要执行remove操作,否则就会出错(因为Tomcat底层使用了线程池)
        conns.remove();
    }

    /**
     * 关闭数据库连接池
     * @param conn
     */
    @Deprecated
    public static void close(Connection conn){
        if (conn != null){

            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
