package dao;

import utils.DBTools;
import java.sql.*;

/*
Qiao Qing
2020/04/04
*/

public class ParameterMgr {

    Connection connection=DBTools.getConnection();
    PreparedStatement statement=null;
    ResultSet rs=null;

    public ParameterMgr() throws SQLException, ClassNotFoundException {
    }

    public int getSmtpState() throws SQLException {
        String getSmtpStateSQL="select smtp_State from parameter";
        statement=connection.prepareStatement(getSmtpStateSQL);
        rs=statement.executeQuery();
        while(rs.next()) {
            return rs.getInt(1);
        }
        return rs.getInt(1);
    }

    public boolean setSmtpState(String newSmtpState) throws SQLException {
        String setSmtpStateSql="update parameter set smtp_state=?";
        statement=connection.prepareStatement(setSmtpStateSql);
        statement.setString(1,newSmtpState);
        statement.executeUpdate();
        return true;
    }

    public int getSmtpPort() throws SQLException {
        String getSmtpPortSql="select smtp_port from parameter";
        statement=connection.prepareStatement(getSmtpPortSql);
        rs=statement.executeQuery();
        while(rs.next()) {
            return rs.getInt(1);
        }
        return rs.getInt(1);
    }

    public boolean setSmtpPort(int newSmtpPort) throws SQLException {
        String setSmtpPortSql="update parameter set smtp_port=?";
        statement=connection.prepareStatement(setSmtpPortSql);
        statement.setInt(1,newSmtpPort);
        statement.executeUpdate();
        return true;
    }

    public int getPop3State() throws SQLException {
        String getPop3StateSQL="select pop3_state from parameter";
        statement=connection.prepareStatement(getPop3StateSQL);
        rs=statement.executeQuery();
        while(rs.next()) {
            return rs.getInt(1);
        }
        return rs.getInt(1);
    }

    public boolean setPop3State(String newPop3State) throws SQLException {
        String setPop3StateSql="update parameter set pop3_state=?";
        statement=connection.prepareStatement(setPop3StateSql);
        statement.setString(1,newPop3State);
        statement.executeUpdate();
        return true;
    }

    public int getPop3Port() throws SQLException {
        String getPop3PortSql="select pop3_port from parameter";
        statement=connection.prepareStatement(getPop3PortSql);
        rs=statement.executeQuery();
        while(rs.next()) {
            return rs.getInt(1);
        }
        return rs.getInt(1);
    }

    public boolean setPop3Port(int newPop3Port) throws SQLException {
        String setPop3PortSql="update parameter set pop3_port=?";
        statement=connection.prepareStatement(setPop3PortSql);
        statement.setInt(1,newPop3Port);
        statement.executeUpdate();
        return true;
    }

    public String getDomainName() throws SQLException {
        String getDomainNameSql="select domain_name from parameter";
        statement=connection.prepareStatement(getDomainNameSql);
        rs=statement.executeQuery();
        while(rs.next()) {
            return rs.getString(1);
        }
        return rs.getString(1);
    }

    public boolean setDomainName(String newDomainName) throws SQLException {
        String setDomainNameSql="update parameter set domain_name=?";
        statement=connection.prepareStatement(setDomainNameSql);
        statement.setString(1,newDomainName);
        statement.executeUpdate();
        return true;
    }

    public int getUserSize() throws SQLException {
        String getUserSizeSql="select user_size from parameter";
        statement=connection.prepareStatement(getUserSizeSql);
        rs=statement.executeQuery();
        while(rs.next()) {
            return rs.getInt(1);
        }
        return rs.getInt(1);
    }

    public boolean setUserSize(int newUserSize) throws SQLException {
        String setUserSizeSql="update parameter set user_size=?";
        statement=connection.prepareStatement(setUserSizeSql);
        statement.setInt(1,newUserSize);
        statement.executeUpdate();
        return true;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println(new ParameterMgr().getDomainName());
        System.out.println(new ParameterMgr().getUserSize());
        System.out.println(new ParameterMgr().setDomainName("qiao.com"));
    }
}
