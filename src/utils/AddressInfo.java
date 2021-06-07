package utils;

/*
Qiao Qing
2020/04/04
*/

public class AddressInfo {

    int pop3Port,smtpPort;
    String pop3Server,smtpServer;
    AddressInfo(String pop3Server,int pop3Port,String smtpServer,int smtpPort) {
        this.pop3Server=pop3Server;
        this.pop3Port=pop3Port;
        this.smtpServer=smtpServer;
        this.smtpPort=smtpPort;
    }

    public int getPop3Port() {
        return pop3Port;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public String getPop3Server() {
        return pop3Server;
    }

    public String getSmtpServer() {
        return smtpServer;
    }
}
