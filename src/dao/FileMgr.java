package dao;

/*
Qiao Qing
2020/04/04
*/

import entity.File;
import utils.DBTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileMgr {
    Connection connection= DBTools.getConnection();
    PreparedStatement statement=null;
    ResultSet rs=null;

    public FileMgr() throws SQLException, ClassNotFoundException {}

    public boolean addFile(String filePath,String fileName) throws SQLException {
        String addFileSql="insert into file(file_path,file_name) values (?,?)";
        statement=connection.prepareStatement(addFileSql);
        statement.setString(1,filePath);
        statement.setString(2,fileName);
        statement.executeUpdate();
        return true;
    }

    public boolean delFile(long fileId) throws SQLException {
        String delFileSql="delete from file where file_id=?";
        statement=connection.prepareStatement(delFileSql);
        statement.setLong(1,fileId);
        statement.executeUpdate();
        return true;
    }

    public File getFile(long fileId) throws SQLException {
        String getFileSql="select * from file where file_id=?";
        statement=connection.prepareStatement(getFileSql);
        statement.setLong(1,fileId);
        rs=statement.executeQuery();
        while(rs.next()) {
            return new File(rs.getString("file_path"),
                    rs.getString("file_name"),fileId);
        }
        return new File(rs.getString("file_path"),
                rs.getString("file_name"),fileId);
    }
}
