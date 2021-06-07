package android;

import dao.MailMgr;
import net.sf.json.JSONObject;
import smtp.SMTPDemo;

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

/*
Qiao Qing
2020/04/24
*/
//1:成功

@WebServlet("/AndroidSendOutMailServlet")
public class AndroidSendOutMailServlet extends HttpServlet {

    int getSize(String subject, String content) {
        int rs=0;
        rs+=subject.length();
        rs+=content.length();
        rs/=1024;
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

        String str= URLDecoder.decode(sb.toString(),"UTF-8");
        JSONObject json = JSONObject.fromObject(str);

        String sender = json.getString("senderAccount");
        String to = json.getString("receiverAccount");
        String subject = json.getString("subject");
        String content = json.getString("content");

        //处理sender，用==分开
        String []senderStr=sender.split("==");
        String senderAccount=senderStr[0];
        String senderPassword=senderStr[1];

        //处理to，用分号分开
        String []receiverStr=to.split(";");

        //发送外部邮件，直接调用SMTPDemo
        for(String receiverAccount:receiverStr) {
            if(receiverAccount!=null && receiverAccount!="")
                SMTPDemo.SendMassage(senderAccount,senderPassword,receiverAccount,subject,content);
        }

        //发送完成，写回1
        out.write("1");
        response.flushBuffer();
    }
}