package android;

import dao.MailMgr;
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
import java.util.ArrayList;

/*
Qiao Qing
2020/04/24
*/
//1：成功修改邮件状态为已读

@WebServlet("/AndroidReadMailServlet")
public class AndroidReadMailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    //阅读邮件，那么更改邮件状态为“已读”
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
        JSONObject json = JSONObject.fromObject(str);

        //System.out.println(sb.toString());
        String receiver;
        long mailId = Long.parseLong(json.getString("mailId"));
        MailMgr mailMgr=null;

        try {
            mailMgr=new MailMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            mailMgr.changeMailState(mailId, 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //找到该邮件的receiver
        Mail mail=null;
        try {
            mail=mailMgr.getSpecialMail(mailId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long currSize=mail.getSize();
        receiver=mail.getReceiverAccount();

        CreateDocument.createDocument(receiver);
        WriteLog wl =  new WriteLog();
        wl.write(FileHanding.basepath+"\\"+receiver+"\\POP3",receiver,receiver+"SMTP",10);


        //添加该用户的已用量
        UserMgr userMgr=null;
        try {
            userMgr=new UserMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            userMgr.addUsedSize(receiver,(int)currSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.write("1");
        response.flushBuffer();
    }
}