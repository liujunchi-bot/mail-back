package android;

import client.SocketClient;
import dao.MailMgr;
import dao.ParameterMgr;
import dao.UserMgr;
import entity.Mail;
import entity.User;
import net.sf.json.JSONObject;
import utils.logtool.CreateDocument;
import utils.logtool.FileHanding;
import utils.logtool.WriteLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/*
Qiao Qing
2020/04/24
*/

//返回1：发送内部邮件成功
//返回2：收件人不存在
//返回3：系统的SMTP已被管理员禁用
//返回4：收件人禁用了POP3
//返回5：发件人禁用了SMTP

@WebServlet("/AndroidSendInnerMailServlet")
public class AndroidSendInnerMailServlet extends HttpServlet {

    int getSize(String subject, String content) {
        int rs=0;
        rs+=subject.length(); //主题
        rs+=content.length(); //内容
        return rs;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        PrintWriter out = response.getWriter();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        String str = URLDecoder.decode(sb.toString(),"UTF-8");
        JSONObject json = JSONObject.fromObject(str);

        String username = json.getString("senderAccount");
        String receiver = json.getString("receiverAccount");
        String subject = json.getString("subject");
        String content = json.getString("content");

        boolean receiverAccountAvailable=false;
        boolean SMTPAvailable=false;

        //0. 发件人是否禁用了SMTP
        UserMgr userMgr=null;
        try {
            userMgr=new UserMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<User> userArrayList=null;
        try {
            userArrayList=userMgr.getUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(userArrayList!=null && userArrayList.size()!=0
        && userArrayList.get(0).getSmtpstate()!=0) {
            //发件人没有禁用SMTP

            //循环遍历所有的收件人
            String []receiverStr=receiver.split(";");
            for(String to:receiverStr) {
                if(to!=null&&to!="") {
                    //1. 是否能找到收件人
                    // 若能找到，则放入数据库，状态为0，暂不发送

                    try {
                        userArrayList=userMgr.getUser(to);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    if(userArrayList==null || userArrayList.size()==0) {
                        //未找到收件人用户
                        receiverAccountAvailable=false;
                    } else {
                        receiverAccountAvailable=true;
                    }

                    MailMgr mailMgr=null;
                    long thisMailId=0;
                    try {
                        mailMgr=new MailMgr();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    if(receiverAccountAvailable) {
                        //收件人存在，将邮件存入数据库，邮件状态为0
                        try {
                            Timestamp timestamp=new Timestamp(System.currentTimeMillis());
                            mailMgr.addMail(username, to,
                                    timestamp, subject,content, 0,getSize(subject,content));
                            thisMailId=mailMgr.getMailId(username, to,
                                    subject,content, 0,getSize(subject,content));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //收件人不存在
                        out.write("2");
                        response.flushBuffer();
                    }

                    if(receiverAccountAvailable) {
                        //2. 管理员是否禁用了SMTP
                        ParameterMgr parameterMgr=null;
                        try {
                            parameterMgr=new ParameterMgr();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        try {
                            if(parameterMgr.getSmtpState()==0) {
                                SMTPAvailable=false;
                                System.out.println("管理员关闭了");
                            } else {
                                SMTPAvailable=true;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        if(!SMTPAvailable) {
                            //SMTP禁用
                            out.write("3");
                            response.flushBuffer();
                        }
                    }

                    if(receiverAccountAvailable && SMTPAvailable) {
                        //3. 收件人是否允许接收
                        User currReceiver=userArrayList.get(0);
                        if(currReceiver.getPop3State()==0) {
                            //收件人拒绝接收
                            System.out.println("收件人关闭了");
                            out.write("4");
                            response.flushBuffer();
                        } else {
                            //收件人允许接收
                            SocketClient.sendMail(username, to, subject, content);
                            //修改邮件状态为已发送
                            try {

                                mailMgr.changeMailState(thisMailId, 1);
                                System.out.println(thisMailId+"修改为已成功");

                                //记录日志
                                CreateDocument.createDocument(username);
                                WriteLog wl =  new WriteLog();
                                wl.write(FileHanding.basepath+"\\"+username+"\\SMTP",username,username+"SMTP",10);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            out.write("1");
                            response.flushBuffer();
                        }
                    }

                }
            }
        } else {
            //发件人禁用了SMTP
            out.write("5");
            response.flushBuffer();
        }
    }
}
