package dao;

import entity.User;
import utils.DBTools;
import java.sql.*;
import java.util.ArrayList;

/*
Qiao Qing
2020/04/04
*/

public class UserMgr {
    Connection connection=DBTools.getConnection();
    PreparedStatement statement=null;
    ResultSet rs=null;

    public UserMgr() throws SQLException, ClassNotFoundException {

    }

    public boolean addUser(String name,String account,String password) throws SQLException {
        String addUserSql="insert into user(nickname,account,password,role,smtpstate,pop3state,usedsize)" +
                " values (?,?,?,1,1,1,0)";
        statement=connection.prepareStatement(addUserSql);
        statement.setString(1,name);
        statement.setString(2,account);
        statement.setString(3,password);
        statement.executeUpdate();
        return true;
    }

    //添加用户，指定权限
    public boolean addUser(String name,String account,String password,int role) throws SQLException {
        String addUserSql="insert into user(nickname,account,password,role,smtpstate,pop3state,usedsize)" +
                " values (?,?,?,?,1,1,0)";
        statement=connection.prepareStatement(addUserSql);
        statement.setString(1,name);
        statement.setString(2,account);
        statement.setString(3,password);
        statement.setInt(4,role);
        statement.executeUpdate();
        return true;
    }

    //  constructor:
    //  User(String nickname,String account,String password,int role,int smtpstate,int pop3State,int usedsize)
    public ArrayList<User> getUser() throws SQLException {
        String getUserSql="select * from user";
        statement=connection.prepareStatement(getUserSql);
        rs=statement.executeQuery();
        ArrayList<User> list=new ArrayList<User>();
        while(rs.next()) {
            list.add(new User(
                    rs.getString("nickname"),
                    rs.getString("account"),
                    rs.getString("password"),
                    rs.getInt("role"),
                    rs.getInt("smtpstate"),
                    rs.getInt("pop3state"),
                    rs.getInt("usedsize")));
        }
        return list;
    }

    public ArrayList<User> getUser(String username) throws SQLException {
        String getUserSql="select * from user where account=?";
        statement=connection.prepareStatement(getUserSql);
        statement.setString(1,username);
        rs=statement.executeQuery();
        ArrayList<User> list=new ArrayList<User>();
        while(rs.next()) {
            list.add(new User(
                    rs.getString("nickname"),
                    rs.getString("account"),
                    rs.getString("password"),
                    rs.getInt("role"),
                    rs.getInt("smtpstate"),
                    rs.getInt("pop3state"),
                    rs.getInt("usedsize")));
        }
        return list;
    }

    public ArrayList<User> getUser(String username, String password) throws SQLException {
        String getUserSql="select * from user where account=? and password=?";
        statement=connection.prepareStatement(getUserSql);
        statement.setString(1,username);
        statement.setString(2,password);
        rs=statement.executeQuery();
        ArrayList<User> list=new ArrayList<User>();
        while(rs.next()) {
            list.add(new User(
                    rs.getString("nickname"),
                    rs.getString("account"),
                    rs.getString("password"),
                    rs.getInt("role"),
                    rs.getInt("smtpstate"),
                    rs.getInt("pop3state"),
                    rs.getInt("usedsize")));
        }
        return list;
    }

    public ArrayList<User> getUser(String username, String password, String nickname) throws SQLException {
        String getUserSql="select * from user where account=? " +
                "and password=? and nickname=?";
        statement=connection.prepareStatement(getUserSql);
        statement.setString(1,username);
        statement.setString(2,password);
        statement.setString(3,nickname);
        rs=statement.executeQuery();
        ArrayList<User> list=new ArrayList<User>();
        while(rs.next()) {
            list.add(new User(
                    rs.getString("nickname"),
                    rs.getString("account"),
                    rs.getString("password"),
                    rs.getInt("role"),
                    rs.getInt("smtpstate"),
                    rs.getInt("pop3state"),
                    rs.getInt("usedsize")));
        }
        return list;
    }

    public boolean changeNickname(String account, String newNickname) throws SQLException {
        String changeNicknameSql="update user set nickname=? where account=?";
        statement=connection.prepareStatement(changeNicknameSql);
        statement.setString(1,newNickname);
        statement.setString(2,account);
        statement.executeUpdate();
        return true;
    }

    public boolean changeRole(String account, int role) throws SQLException {
        String changeNicknameSql="update user set role=? where account=?";
        statement=connection.prepareStatement(changeNicknameSql);
        statement.setInt(1,role);
        statement.setString(2,account);
        statement.executeUpdate();
        return true;
    }

    public boolean delUser(String account) throws SQLException {
        String delUserSql="delete from user where account=?";
        statement=connection.prepareStatement(delUserSql);
        statement.setString(1,account);
        System.out.println(statement.executeUpdate());
        return true;
    }

    public boolean changeUserRole(String account, int newRole) throws SQLException {
        String changeUserRoleSql="update user set role=? where account=?";
        statement=connection.prepareStatement(changeUserRoleSql);
        statement.setInt(1,newRole);
        statement.setString(2,account);
        statement.executeUpdate();
        return true;
    }

    public boolean changeStmpstate(String account, int stmpstate) throws SQLException {
        String changeStmpstateSql="update user set stmpstate=? where account=?";
        statement=connection.prepareStatement(changeStmpstateSql);
        statement.setInt(1,stmpstate);
        statement.setString(2,account);
        statement.executeUpdate();
        return true;
    }

    public boolean changePop3state(String account, int pop3state) throws SQLException {
        String changePop3stateSql="update user set pop3state=? where account=?";
        statement=connection.prepareStatement(changePop3stateSql);
        statement.setInt(1,pop3state);
        statement.setString(2,account);
        statement.executeUpdate();
        return true;
    }


    public boolean changePassword(String account, String newPassword) throws SQLException{
        String changePasswordSql="update user set password=? where account =?";
        statement=connection.prepareStatement(changePasswordSql);
        statement.setString(2,account);
        statement.setString(1,newPassword);
        statement.executeUpdate();
        return true;
    }

    public int getUsedSize(String account) throws SQLException {
        String usedSizeSql="select usedSize from user where account=?";
        statement=connection.prepareStatement(usedSizeSql);
        statement.setString(1,account);
        rs=statement.executeQuery();
        while(rs.next()) {
            return rs.getInt(1);
        }
        return rs.getInt(1);
    }

    //增加用户使用大小
    public boolean addUsedSize(String account, int delta) throws SQLException {
        String addUsedSizeSql="update user set usedsize=usedsize+? where account=?";
        statement=connection.prepareStatement(addUsedSizeSql);
        statement.setInt(1,delta);
        statement.setString(2,account);
        statement.executeUpdate();
        return true;
    }

    //减少用户使用大小
    public boolean subUsedSize(String account, int delta) throws SQLException {
        String addUsedSizeSql="update user set usedsize=usedsize-? where account=?";
        statement=connection.prepareStatement(addUsedSizeSql);
        statement.setInt(1,delta);
        statement.setString(2,account);
        statement.executeUpdate();
        return true;
    }

    //直接修改用户已使用大小
    public boolean changeUsedSize(String account, int newSize) throws SQLException {
        String changeUsedSizeSql="update user set usedsize=? where account=?";
        statement=connection.prepareStatement(changeUsedSizeSql);
        statement.setInt(1,newSize);
        statement.setString(2,account);
        statement.executeUpdate();
        return true;
    }

    public static void main(String[] args) {

    }
}