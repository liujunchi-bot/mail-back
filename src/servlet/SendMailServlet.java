package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import entity.*;
import  dao.*;
import smtp.*;
//import servlet.SMTPDemo;

import java.util.ArrayList;
import java.sql.*;
import client.*;
import utils.logtool.CreateDocument;
import utils.logtool.FileHanding;
import utils.logtool.WriteLog;

/*
发送邮件，涉及邮件多发，然后发送邮件时先把邮件信息存储到数据库。状态设为0表示未发送
然后要把发件人的已用内存更新，叠加当前文件的大小，单位字节。
检查如果管理员没有禁用smtp服务，那么就可以执行发送函数。
发送函数check是检索该用户的发件箱，然后确定该用户的smtp服务是否开启，查看未发送的逐一发送，且改变邮件状态。变为1已发送。
 */
@WebServlet("/SendMailServlet")
public class SendMailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
    public void Check(String Account,HttpSession session) throws SQLException, ClassNotFoundException, IOException {

        MailMgr mailmgr=new MailMgr();

        ArrayList <Mail> list = mailmgr.getsendedMail(Account);//查看账户发件箱的邮件
        for(int i=0;i<list.size();i++){
            if(list.get(i).getMailState()==0){ //如果状态是未发送
                User u = null;
                UserMgr user = new UserMgr();
                ArrayList <User> userlist= user.getUser();
                for(int j=0;j<userlist.size();j++){   //从用户表中获取收件用户。
                    if(userlist.get(j).getAccount().equals(Account)){
                        u=userlist.get(j);
                    }
                }
                User u1= null;
                for(int j=0;j<userlist.size();j++){   //从用户表中获取收件用户。
                    if(userlist.get(j).getAccount().equals(list.get(i).getReceiverAccount())){
                        u1=userlist.get(j);
                    }
                }

                if(u.getSmtpstate()==1&&u1.getPop3State()==1){  //如果用户状态是开启了smtp服务，就可以发送邮件
                    SocketClient.sendMail(list.get(i).getSenderAccount(),list.get(i).getReceiverAccount(),list.get(i).getSubject(),list.get(i).getContent());
                    mailmgr.changeMailState(list.get(i).getMailId(),1);//后面的是新状态 1 是发送
                    session.setAttribute("message","发送成功");
                    CreateDocument.createDocument(Account);
                    WriteLog wl =  new WriteLog();
                    wl.write(FileHanding.basepath+"\\"+Account+"\\SMTP",Account,Account+"SMTP",10);
                }
                else{
                    session.setAttribute("message","服务被禁用");
                }
            }
        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        HttpSession session = request.getSession();
        String Theme = request.getParameter("theme");     //主题
        String SendFrom = request.getParameter("send-p");   //发件人
        String PassWord = request.getParameter("ps-p");   //密码
        String SendTo = request.getParameter("rece_p");    //收件人
        String Content = request.getParameter("content");     //内容
        char []a = SendFrom.toCharArray();
        int count=0;
        for(int i=0;i<SendFrom.length();i++){
            if(a[i]=='@'){
                count = i;break;
            }
        }
        SendFrom=SendFrom.substring(0,count);
        System.out.println(SendFrom);
        System.out.println(PassWord);
        System.out.println(SendTo);
        System.out.println(Theme);
        System.out.println(Content);
        User mainuser = null;
        UserMgr mainusermgr = null;
        ArrayList <User> mainuserlist = null;
        try {
            mainusermgr=new UserMgr();
            try {
                mainuserlist =mainusermgr.getUser();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("usersize"+mainuserlist.size());
        for(int i=0;i<mainuserlist.size();i++){
            if(mainuserlist.get(i).getAccount().equals(SendFrom)){
                mainuser = mainuserlist.get(i);break;
            }
        }
        if(mainuser == null){
            session.setAttribute("message","账号不存在");
        }
        else if(!mainuser.getPassword().equals(PassWord)){
            session.setAttribute("message","账号密码不匹配");
        }
        else {
            int State = 0;   //0是未发送状态
            MailMgr mail = null;
            try {
                mail = new MailMgr();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            ParameterMgr parameterMgr = null; //创建转态管理类的实例。
            try {
                parameterMgr =new ParameterMgr();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Timestamp Date = new Timestamp(System.currentTimeMillis());  //获取时间戳

            int Size=Content.length()+Theme.length();
            String [] To = SendTo.split(";");
            if(To.length==1){     //如果此时只有一个目标用户
                int jjjj=0;
                for(int i=0;i<mainuserlist.size();i++){
                    if(mainuserlist.get(i).getAccount().equals(SendTo)){
                        jjjj=1;     //标记位1，说明目标存在。
                    }
                }
                if(jjjj==0){
                    session.setAttribute("message","目标不存在");
                }
            }
            for(int i=0;i<To.length;i++){
                UserMgr user = null;
                try {
                    user=new UserMgr();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                ArrayList <User> userlist= null;
                try {
                    userlist = user.getUser();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                int judge=0;
                for(int j=0;j<userlist.size();j++){
                    if(To[i].equals(userlist.get(j).getAccount())){
                        judge=1;
                    }
                }
                if(judge==1){      //如果接受方存在，再添加记录。
                    try {
                        mail.addMail(SendFrom,To[i],Date,Theme,Content,State,Size);  //把当前要发送的邮件存入邮箱,存在多条记录。
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            UserMgr usermgr = null;  //实例化一个用户管理类
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
                if(userList.get(i).getAccount().equals(SendFrom)){
                    Size=Size+(int)userList.get(i).getUsedsize();
                }
            }
            try {
                usermgr.changeUsedSize(SendFrom,Size);    //把变化写入数据库。
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int t=0;
            try {
                t=parameterMgr.getSmtpState();        //获取管理员设置的smtp状态
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(t==1){                             //如果管理员此时没有禁用邮件服务，那么就执行check函数
                try {
                    Check(SendFrom,session);               //检查该账户所有未发送的一一发送，发送完毕修改状态。

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else{
                session.setAttribute("message","服务被管理员禁用");
            }
        }
        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }
}
