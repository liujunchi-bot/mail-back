package dao;

/*
Qiao Qing
2020/04/04
*/


import entity.Attachment;
import utils.DBTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttachmentMgr {

    /*
    private long mailId;
    private long attachOrder;
    private long fileId;
     */
    Connection connection= DBTools.getConnection();
    PreparedStatement statement=null;
    ResultSet rs=null;

    public AttachmentMgr() throws SQLException, ClassNotFoundException {
    }

    ArrayList<Attachment> getAttachment(long mailId) throws SQLException {
        String getAttachmentSql="select * from attachment where mail_id=? order by attach_order";
        statement=connection.prepareStatement(getAttachmentSql);
        statement.setLong(1,mailId);
        rs=statement.executeQuery();
        ArrayList<Attachment> list=new ArrayList<>();
        while(rs.next()) {
            list.add(new Attachment(rs.getLong("mail_id"),
                    rs.getLong("attach_order"),
                    rs.getLong("file_id")
                    ));
        }
        return list;
    }

    boolean addAttachment(long mailId,long order,long fileId) throws SQLException {
        String addAttachmentSql="insert into attachment(mail_id,attach_order,file_id) values (?,?,?)";
        statement=connection.prepareStatement(addAttachmentSql);
        statement.setLong(1,mailId);
        statement.setLong(2,order);
        statement.setLong(3,fileId);
        statement.executeUpdate();
        return true;
    }

    boolean delAttachment(long mailId,long order) throws SQLException{
        String delAttachmentSql="delete from attachment where mail_id=? and attach_order=?";
        statement=connection.prepareStatement(delAttachmentSql);
        statement.setLong(1,mailId);
        statement.setLong(2,order);
        statement.executeUpdate();
        return true;
    }
}
