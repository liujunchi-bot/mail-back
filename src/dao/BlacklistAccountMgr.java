package dao;

/*
Qiao Qing
2020/04/04
*/

import entity.BlacklistAccount;
import utils.DBTools;

import javax.swing.text.BadLocationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BlacklistAccountMgr {
    Connection connection= DBTools.getConnection();
    PreparedStatement statement=null;
    ResultSet rs=null;

    public BlacklistAccountMgr() throws SQLException, ClassNotFoundException {
    }

    public ArrayList<BlacklistAccount> getBlacklistAccount(String account) throws SQLException {
        ArrayList<BlacklistAccount> list=new ArrayList<>();
        String getBlacklistAccountSql="select * from blacklist_account where host_account=?";
        statement=connection.prepareStatement(getBlacklistAccountSql);
        statement.setString(1,account);
        rs=statement.executeQuery();
        while(rs.next()) {
            list.add(new BlacklistAccount(rs.getString("host_account"),
                    rs.getString("enemy_account")));
        }
        return list;
    }

    public ArrayList<BlacklistAccount> getBlacklistAccount() throws SQLException {
        ArrayList<BlacklistAccount> list=new ArrayList<>();
        String getBlacklistAccountSql="select * from blacklist_account group by host_account,enemy_account";
        statement=connection.prepareStatement(getBlacklistAccountSql);
        rs=statement.executeQuery();
        while(rs.next()) {
            list.add(new BlacklistAccount(rs.getString("host_account"),
                    rs.getString("enemy_account")));
        }
        return list;
    }

    public boolean editBlacklistAccount(String oldHostAccount,String oldEnemyAccount,String newHostAccount,String newEnemyAccount) throws SQLException{
        String editBlacklistAccountSql="update blacklist_account set host_account=?,enemy_account=? " +
                "where host_account=? and enemy_account=?";
        statement=connection.prepareStatement(editBlacklistAccountSql);
        statement.setString(1,newHostAccount);
        statement.setString(2,newEnemyAccount);
        statement.setString(3,oldHostAccount);
        statement.setString(4,oldEnemyAccount);
        statement.executeUpdate();
        return true;
    }

    public boolean addBlacklistAccount(String host_account,String enemy_account) throws SQLException {
        String addBlacklistAccountSql="insert into blacklist_account(host_account,enemy_account) " +
                "values (?,?)";
        statement=connection.prepareStatement(addBlacklistAccountSql);
        statement.setString(1,host_account);
        statement.setString(2,enemy_account);
        statement.executeUpdate();
        return true;
    }

    public boolean delBlacklistAccount(String host_account,String enemy_account) throws SQLException {
        String delBlacklistAccountSql="delete from blacklist_account where host_account=? and enemy_account=?";
        statement=connection.prepareStatement(delBlacklistAccountSql);
        statement.setString(1,host_account);
        statement.setString(2,enemy_account);
        System.out.println("delacc "+statement.executeUpdate());
        return true;
    }
}
