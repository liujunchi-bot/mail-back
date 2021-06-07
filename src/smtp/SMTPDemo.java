package smtp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import utils.AddressInfo;
import utils.AddressParse;
import utils.Base64Util;

public class SMTPDemo {
    String mailServer;
    String from;
    String pass;
    String to;
    String subject;
    String content;
    String lineFeet = "\r\n";
    private int port = 25;

    Socket client;
    BufferedReader in;
    DataOutputStream os;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMailServer() {
        return mailServer;
    }

    public void setMailServer(String mailServer) {
        this.mailServer = mailServer;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port){
        this.port = port;
    }



    private boolean init() {
        boolean boo = true;
        if (mailServer == null || "".equals(mailServer)) {
            return false;
        }
        try {
            System.out.println(mailServer + port);
            client = new Socket(mailServer, port);

            in = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            os = new DataOutputStream(client.getOutputStream());
            String isConnect = response();
            if (isConnect.startsWith("220")) {

            } else {
                System.out.println("��������ʧ�ܣ�" + isConnect);
                boo = false;
            }
        } catch (UnknownHostException e) {
            System.out.println("��������ʧ�ܣ�");
            e.printStackTrace();
            boo = false;
        } catch (IOException e) {
            System.out.println("��ȡ��ʧ�ܣ�");
            e.printStackTrace();
            boo = false;
        }
        return boo;
    }

    private String sendCommand(String msg) {
        String result = null;
        try {
            os.writeBytes(msg);
            os.flush();
            result = response();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String response() {
        String result = null;
        try {
            result = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void close() {
        try {
            os.close();
            in.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean sendMail() {

        if (client == null) {
            if (init()) {

            } else {
                return false;
            }
        }

        if (from == null || from.isEmpty() || to == null || to.isEmpty()) {
            return false;
        }

        String result = sendCommand("HELO " + mailServer + lineFeet);
        if (isStartWith(result, "250")) {
        } else {
            System.out.println("����ʧ�ܣ�" + result);
            return false;
        }
        String result1 = sendCommand("" );
        String result2 = sendCommand("" );
        String auth = sendCommand("AUTH LOGIN" + lineFeet);
        if (isStartWith(auth, "334")) {
        } else {
            return false;
        }
        String user = sendCommand(new String(Base64Util.encode(from.getBytes()))
                + lineFeet);
        if (isStartWith(user, "334")) {
        } else {
            return false;
        }
        String p = sendCommand(new String(Base64Util.encode(pass.getBytes()))
                + lineFeet);
        if (isStartWith(p, "235")) {
        } else {
            return false;
        }

        String f = sendCommand("Mail From:<" + from + ">" + lineFeet);
        if (isStartWith(f, "250")) {
        } else {
            return false;
        }
        String toStr = sendCommand("RCPT TO:<" + to + ">" + lineFeet);
        if (isStartWith(toStr, "250")) {
        } else {
            return false;
        }

        String data = sendCommand("DATA" + lineFeet);
        if (isStartWith(data, "354")) {
        } else {
            return false;
        }

        //��ȡ��ǰʱ��
        Date date = new Date();


        StringBuilder sb = new StringBuilder();
        sb.append("From:<" + from + ">" + lineFeet);
        sb.append("To:<" + to + ">" + lineFeet);
        sb.append("Subject:" + subject + lineFeet);
        sb.append("Date:" + date.toString() + lineFeet);
        sb.append("Content-Type:text/plain;charset=\"GB2312\"" + lineFeet);
        sb.append(lineFeet);
        sb.append(content);
        sb.append(lineFeet + "." + lineFeet);

        String conStr = sendCommand(sb.toString());
        if (isStartWith(conStr, "250")) {
        } else {
            return false;
        }

        String quit = sendCommand("QUIT" + lineFeet);
        if (isStartWith(quit, "221")) {
        } else {
            return false;
        }
        close();
        return true;
    }

//	public static void setServerAndPort(SMTPDemo mail){
//		String [] names = {"sina.com","sina.cn","����vip","�Ѻ�",};
//		String [] serverPop3 = {"smtp.sina.com.cn","smtp.sina.com","smtp.vip.sina.com","smtp.sohu.com"};
//		int [] pop3Port = {25,25,25,25};
//		String [] serverSmtp = {"smtp.sina.com.cn","smtp.sina.com","smtp.vip.sina.com","smtp.sohu.com"};
//		int [] smtpPort = {25,25,25,25};
//
//		for(int i=0;i<)
//
//		mail.setMailServer();
//
//	}

    /**
     *
     * @param sendFrom   ������
     * @param password   ����   �п�������Ȩ��(��qq)
     * @param sendTo     ������
     * @param subject    �ʼ�����
     * @param content    ����
     * @return   �Ƿ�ɹ�
     */
    public static boolean  SendMassage(String sendFrom,String password,String sendTo,String subject,String content){
        String tail = sendFrom.substring(sendFrom.indexOf("@"));
        System.out.println(tail);
        SMTPDemo mail = new SMTPDemo();

        AddressInfo info=new AddressParse().parse(tail);
        if(info==null || "".equals(info)){
            System.out.println("暂不支持该邮箱域名！");
            return false;   //����ʧ��
        }

        mail.setMailServer(info.getSmtpServer());
        System.out.println(mail.getMailServer());
        mail.setPort(info.getSmtpPort());
        System.out.println(mail.getPort());
        mail.setFrom(sendFrom);
        mail.setPass(password);
        mail.setTo(sendTo);
        mail.setSubject(subject);
        mail.setContent(content);
        boolean boo = mail.sendMail();
        if (boo)
            System.out.println("邮件发送成功！");
        else {
            System.out.println("邮件发送失败！");
        }
        return boo;
    }

    private boolean isStartWith(String res, String with) {
        return res.startsWith(with);
    }

    public static void main(String[] args) {
//		MrMiao17@hnu.edu.cn
        SendMassage("285013442@qq.com","gfldkaryelpobgfd","79555574@qq.com" ,"test1","i am a boy!");

    }
}
