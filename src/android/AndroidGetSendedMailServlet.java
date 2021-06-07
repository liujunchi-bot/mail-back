package android;

import dao.MailMgr;
import entity.Mail;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utils.MailHelp;

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


@WebServlet("/AndroidGetSendedMailServlet")
public class AndroidGetSendedMailServlet extends HttpServlet {

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

        String str= URLDecoder.decode(sb.toString(),"UTF-8");
        JSONObject jsonObject = JSONObject.fromObject(str);
        String username=jsonObject.getString("account");

        MailMgr mailMgr=null;
        try {
            mailMgr=new MailMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Mail> list= null;
        try {
            list = mailMgr.getsendedMail(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray=new JSONArray();
        for(Mail mail:list) {

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

            long mailId=mail.getMailId();
            String senderAccount=mail.getSenderAccount();
            String receiverAccount=mail.getReceiverAccount();
            Timestamp dateTime=mail.getDateTime();
            String subject=mail.getSubject();
            String content=mail.getContent();
            long mailState=mail.getMailState();
            long size=mail.getSize();

            MailHelp mailHelp=new MailHelp( mailId,  senderAccount,  receiverAccount,  dateTime,
                    subject,  content,  mailState,  size) ;

            JSONObject obj =new JSONObject();
            obj.put("mailId",mailHelp.getMailId());
            obj.put("senderAccount",mailHelp.getSenderAccount());
            obj.put("receiverAccount",mailHelp.getReceiverAccount());
            obj.put("dateTime",mailHelp.getDateTime());
            obj.put("subject",mailHelp.getSubject());
            obj.put("content",mailHelp.getContent());
            obj.put("mailState",mailHelp.getMailState());
            obj.put("size",mailHelp.getSize());
            jsonArray.add(obj);

        }
        out.write(jsonArray.toString());
        response.flushBuffer();
    }
}
