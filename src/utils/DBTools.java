package utils;

import java.sql.*;

/*
Qiao Qing
2020/04/04
*/

//封装数据库连接
public class DBTools {
    private static String url="jdbc:mysql://localhost:3306/wemail?useUnicode=true&serverTimezone=Hongkong&characterEncoding=utf-8";
    private static String username="root";
    private static String password="xuanjunchi520";
    private static Connection connection=null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if(connection==null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(url,username,password);
        }
        return connection;
    }
}
