package dao;

/*
Qiao Qing
2020/04/05
*/

import entity.BlacklistIp;
import utils.DBTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BlacklistIpMgr {

    Connection connection= DBTools.getConnection();
    PreparedStatement statement=null;
    ResultSet rs=null;

    public BlacklistIpMgr() throws SQLException, ClassNotFoundException {
    }

    public ArrayList<BlacklistIp> getBlacklistIp(String account) throws SQLException {
        ArrayList<BlacklistIp> list=new ArrayList<>();
        String getBlacklistIpSql="select * from blacklist_ip where host_account=?";
        statement=connection.prepareStatement(getBlacklistIpSql);
        statement.setString(1,account);
        rs=statement.executeQuery();
        while(rs.next()) {
            list.add(new BlacklistIp(rs.getString("host_account"),
                    rs.getString("enemy_ip")));
        }
        return list;
    }

    public ArrayList<BlacklistIp> getBlacklistIp() throws SQLException {
        ArrayList<BlacklistIp> list=new ArrayList<>();
        String getBlacklistIpSql="select * from blacklist_ip group by host_account,enemy_IP";
        statement=connection.prepareStatement(getBlacklistIpSql);
        rs=statement.executeQuery();
        while(rs.next()) {
            list.add(new BlacklistIp(rs.getString("host_account"),
                    rs.getString("enemy_ip")));
        }
        return list;
    }

    public boolean editBlacklistIp(String oldHostAccount,String oldEnemyIp,String newHostAccount,String newEnemyIp) throws SQLException{
        String editBlacklistIpSql="update blacklist_ip set host_account=?,enemy_ip=? " +
                "where host_account=? and enemy_ip=?";
        statement=connection.prepareStatement(editBlacklistIpSql);
        statement.setString(1,newHostAccount);
        statement.setString(2,newEnemyIp);
        statement.setString(3,oldHostAccount);
        statement.setString(4,oldEnemyIp);
        statement.executeUpdate();
        return true;
    }

    public boolean addBlacklistIp(String host_account,String enemy_ip) throws SQLException {
        String addBlacklistIpSql="insert into blacklist_ip(host_account,enemy_ip) " +
                "values (?,?)";
        statement=connection.prepareStatement(addBlacklistIpSql);
        statement.setString(1,host_account);
        statement.setString(2,enemy_ip);
        statement.executeUpdate();
        return true;
    }

    public boolean delBlacklistIp(String host_account,String enemy_ip) throws SQLException {
        String delBlacklistIpSql="delete from blacklist_ip where host_account=? and enemy_ip=?";
        statement=connection.prepareStatement(delBlacklistIpSql);
        statement.setString(1,host_account);
        statement.setString(2,enemy_ip);
        System.out.println("delip "+statement.executeUpdate());
        return true;
    }
}
