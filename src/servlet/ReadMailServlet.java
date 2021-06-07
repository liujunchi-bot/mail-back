package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.*;
import dao.*;
import utils.logtool.CreateDocument;
import utils.logtool.FileHanding;
import utils.logtool.WriteLog;

@WebServlet("/ReadMailServlet")
public class ReadMailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");

        HttpSession session=request.getSession();
        String Mail_Id =request.getParameter("keyID");//获取到邮箱id
        String username =session.getAttribute("username").toString();
        String nickname = session.getAttribute("nickname").toString();
        String sendfrom="" ;
        String sendto="" ;
        String Date="";
        String Content="";
        String Theme="";
        MailMgr mailMgr =null;
        try {
            mailMgr =new MailMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Mail> mailList=null;
        try {
            mailList = mailMgr.getReceivedMail();  //收件箱中查找
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int Size=0;
        for(int i=0;i<mailList.size();i++){        //遍历收件箱
            if(mailList.get(i).getMailId()==Integer.valueOf(Mail_Id)){   //找到匹配的mailId然后获取他的内容
                Content=mailList.get(i).getContent();
                sendfrom=mailList.get(i).getSenderAccount();
                sendto=mailList.get(i).getReceiverAccount();
                Date=mailList.get(i).getDateTime().toString();
                Theme=mailList.get(i).getSubject();
                Size = Content.length();  //获取信件长度。
                try {
                    mailMgr.changeMailState(Integer.valueOf(Mail_Id),2);   //标记为已读
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        UserMgr usermgr = null;
        ArrayList <User> userList=null;
        try {
            usermgr=new UserMgr();
            try {
                userList= usermgr.getUser();  //找到该用户。他的用户已用空间相应的变大
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        for(int i=0;i<userList.size();i++){
            if(userList.get(i).getAccount().equals(sendto)){
                Size=Size+(int)userList.get(i).getUsedsize();
            }
        }
        try {
            usermgr.changeUsedSize(sendto,Size);    //把变化写入数据库。
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CreateDocument.createDocument(username);
        WriteLog wl =  new WriteLog();
        wl.write(FileHanding.basepath+"\\"+username+"\\POP3",username,username+"SMTP",10);
        session.setAttribute("content",Content); //把content内容带着传过来的参数跳转到下一个页面。
        session.setAttribute("date",Date);
        session.setAttribute("sendfrom",sendfrom);
        session.setAttribute("sendto",sendto);
        session.setAttribute("username",username);
        session.setAttribute("nickname",nickname);
        session.setAttribute("Theme",Theme);
//        request.getRequestDispatcher("/login.jsp").forward(request, response);
        response.getWriter().write("success");
        response.flushBuffer();
    }
}