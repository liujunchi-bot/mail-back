package dao;

import entity.Mail;
import utils.DBTools;
import java.sql.*;
import java.util.ArrayList;

/*
Qiao Qing
2020/04/04
*/

public class MailMgr {
    /*
  private long mailId;
  private String senderAccount;
  private String receiverAccount;
  private java.sql.Timestamp dateTime;
  private String subject;
  private String content;
  private long mailState;
  private long size;
     */
    Connection connection=DBTools.getConnection();
    PreparedStatement statement=null;
    ResultSet rs=null;

    public MailMgr() throws SQLException, ClassNotFoundException {

    }

    public ArrayList<Mail> getsendedMail() throws SQLException {
        String getsendedMailSql="select * from mail order by date_time desc";
        statement=connection.prepareStatement(getsendedMailSql);
        rs=statement.executeQuery();
        ArrayList<Mail> list=new ArrayList<Mail>();
        while(rs.next()) {
            list.add(new Mail(rs.getLong("mail_id"),rs.getString("sender_account"),
                    rs.getString("receiver_account"),rs.getTimestamp("date_time"),
                    rs.getString("subject"),rs.getString("content"),
                    rs.getLong("mail_state"),rs.getLong("size")
            ));
        }
        return list;
    }

    public ArrayList<Mail> getsendedMail(String account) throws SQLException {
        String getsendedMailSql="select * from mail where sender_account=? order by date_time desc";
        statement=connection.prepareStatement(getsendedMailSql);
        statement.setString(1,account);
        rs=statement.executeQuery();
        ArrayList<Mail> list=new ArrayList<Mail>();
        while(rs.next()) {
            list.add(new Mail(rs.getLong("mail_id"),rs.getString("sender_account"),
                    rs.getString("receiver_account"),rs.getTimestamp("date_time"),
                    rs.getString("subject"),rs.getString("content"),
                    rs.getLong("mail_state"),rs.getLong("size")
            ));
        }
        return list;
    }

    //注：已过滤黑名单中的账户
    public ArrayList<Mail> getReceivedMail() throws SQLException {
        String getReceivedMailSql="select * from mail order by date_time desc";
        statement=connection.prepareStatement(getReceivedMailSql);
        rs=statement.executeQuery();
        ArrayList<Mail> list=new ArrayList<Mail>();
        while(rs.next()) {
            list.add(new Mail(rs.getLong("mail_id"),rs.getString("sender_account"),
                    rs.getString("receiver_account"),rs.getTimestamp("date_time"),
                    rs.getString("subject"),rs.getString("content"),
                    rs.getLong("mail_state"),rs.getLong("size")
            ));
        }
        return list;
    }
    public ArrayList<Mail> getReceivedMail(String account) throws SQLException {
        String getReceivedMailSql="select * from mail where receiver_account=? order by date_time desc" +
                "and sender_account not in(select enemy_account from blacklist_account where host_account=?)";
        statement=connection.prepareStatement(getReceivedMailSql);
        statement.setString(1,account);
        statement.setString(2,account);
        rs=statement.executeQuery();
        ArrayList<Mail> list=new ArrayList<Mail>();
        while(rs.next()) {
            list.add(new Mail(rs.getLong("mail_id"),rs.getString("sender_account"),
                    rs.getString("receiver_account"),rs.getTimestamp("date_time"),
                    rs.getString("subject"),rs.getString("content"),
                    rs.getLong("mail_state"),rs.getLong("size")
            ));
        }
        return list;
    }


    public ArrayList<Mail> getsendedMail(String account, long state) throws SQLException {
        String getsendedMailSql="select * from mail where sender_account=? and mail_state=? order by date_time desc";
        statement=connection.prepareStatement(getsendedMailSql);
        statement.setString(1,account);
        statement.setLong(2,state);
        rs=statement.executeQuery();
        ArrayList<Mail> list=new ArrayList<Mail>();
        while(rs.next()) {
            list.add(new Mail(rs.getLong("mail_id"),rs.getString("sender_account"),
                    rs.getString("receiver_account"),rs.getTimestamp("date_time"),
                    rs.getString("subject"),rs.getString("content"),
                    rs.getLong("mail_state"),rs.getLong("size")
            ));
        }
        return list;
    }

    public boolean addMail(String senderAccount,String receiverAccount,Timestamp dateTime,
                           String subject,String content,long mailState,long size) throws SQLException {
        String addMailSql="insert into mail(sender_account,receiver_account,date_time,subject,content,mail_state,size) values (?,?,?,?,?,?,?)";
        statement=connection.prepareStatement(addMailSql);
        statement.setString(1,senderAccount);
        statement.setString(2,receiverAccount);
        statement.setTimestamp(3,dateTime);
        statement.setString(4,subject);
        statement.setString(5,content);
        statement.setLong(6,mailState);
        statement.setLong(7,size);
        statement.executeUpdate();
        return true;
    }

    //注：已过滤黑名单中的账户
    public ArrayList<Mail> getReceivedMail(String account, long state) throws SQLException {
        String getReceivedMailSql="select * from mail where receiver_account=? and mail_state=? " +
                "and sender_account not in (select enemy_account from blacklist_account where host_account=?)";
        statement=connection.prepareStatement(getReceivedMailSql);
        statement.setString(1,account);
        statement.setLong(2,state);
        statement.setString(3,account);
        rs=statement.executeQuery();
        ArrayList<Mail> list=new ArrayList<Mail>();
        while(rs.next()) {
            list.add(new Mail(rs.getLong("mail_id"),rs.getString("sender_account"),
                    rs.getString("receiver_account"),rs.getTimestamp("date_time"),
                    rs.getString("subject"),rs.getString("content"),
                    rs.getLong("mail_state"),rs.getLong("size")
            ));
        }
        return list;
    }

    public Mail getSpecialMail(long mailId) throws SQLException {
        String getSpecialMailSql="select * from mail where mail_id=?";
        statement=connection.prepareStatement(getSpecialMailSql);
        statement.setLong(1,mailId);
        rs=statement.executeQuery();
        ArrayList<Mail> list=new ArrayList<Mail>();
        while(rs.next()) {
            return new Mail(rs.getLong("mail_id"),rs.getString("sender_account"),
                    rs.getString("receiver_account"),rs.getTimestamp("date_time"),
                    rs.getString("subject"),rs.getString("content"),
                    rs.getLong("mail_state"),rs.getLong("size")
            );
        }
        return null;
    }

    public long getMailId(String senderAccount,String receiverAccount,
                           String subject,String content,long mailState,long size) throws SQLException {
        String addMailSql="select mail_id from mail where sender_account=? " +
                "and receiver_account=? and subject=? and " +
                "content=? and mail_state=? and size=?";
        statement=connection.prepareStatement(addMailSql);
        statement.setString(1,senderAccount);
        statement.setString(2,receiverAccount);
        statement.setString(3,subject);
        statement.setString(4,content);
        statement.setLong(5,mailState);
        statement.setLong(6,size);
        ResultSet rs=statement.executeQuery();
        while(rs.next()) {
            return rs.getLong(1);
        }
        return -1;
    }

    public boolean delMail(long mail_id) throws SQLException {
        String delMailSql="delete from mail where mail_id=?";
        statement=connection.prepareStatement(delMailSql);
        statement.setLong(1,mail_id);
        System.out.println(statement.executeUpdate());
        return true;
    }

    public boolean changeMailState(long mailId,long newMailState) throws SQLException {
        String changeMailStateSql="update mail set mail_state=? where mail_id=?";
        statement=connection.prepareStatement(changeMailStateSql);
        statement.setLong(1,newMailState);
        statement.setLong(2,mailId);
        statement.executeUpdate();
        return true;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new MailMgr().addMail("qiao","miao",
                new Timestamp(System.currentTimeMillis()),"Hello",
                "Hello, Miao!", 0, 10);
        //new MailMgr().getMailId("miao","qiao",)
    }
}
