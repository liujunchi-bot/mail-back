package dao;

/*
Qiao Qing
2020/04/04
*/

import entity.Contact;
import utils.DBTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactMgr {

    Connection connection= DBTools.getConnection();
    PreparedStatement statement=null;
    ResultSet rs=null;

    public ContactMgr() throws SQLException, ClassNotFoundException {
    }

    public ArrayList<Contact> getContact(String hostAccount) throws SQLException{
        String getContactSql="select * from contact where host_account=?";
        ArrayList<Contact> list=new ArrayList<>();
        statement=connection.prepareStatement(getContactSql);
        statement.setString(1,hostAccount);
        rs=statement.executeQuery();
        while(rs.next()) {
            list.add(new Contact(rs.getLong("con_id"),
                    rs.getString("host_account"),
                    rs.getString("friend_account")));
        }
        return list;
    }

    public boolean delContact(long conID) throws SQLException {
        String delContactSql="delete from contact where con_id=?";
        statement=connection.prepareStatement(delContactSql);
        statement.setLong(1,conID);
        statement.executeUpdate();
        return true;
    }

    public boolean addContact(String hostAccount, String friendAccount) throws SQLException {
        String delContactSql="insert into contact(host_account,friend_account) values (?,?)";
        statement=connection.prepareStatement(delContactSql);
        statement.setString(1,hostAccount);
        statement.setString(2,friendAccount);
        statement.executeUpdate();
        return true;
    }
}