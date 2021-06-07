package servlet;

import dao.UserMgr;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.*;
//用于登陆时忘记密码
@WebServlet("/FindPasswordServlet")
public class FindPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        String username = request.getParameter("main-address");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("newPassword");

        UserMgr usermgr = null;
        try {
            usermgr = new UserMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<User> userlist =null;
        System.out.println("开始找人"+ username + " " + nickname + " " +  password);
        try {
            userlist=usermgr.getUser(username);
            System.out.println(userlist.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        int Judge=0;
        if(userlist.size()==0){
            session.setAttribute("message","账号未注册！");
        }
        else if(!userlist.get(0).getNickname().equals(nickname)){
            session.setAttribute("message","用户信息错误！");
        }
        else{
            System.out.println("开始改密码");
            try {
                usermgr.changePassword(username,password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("完成修改");
            session.setAttribute("message","密码修改成功！");
            session.setAttribute("username",username);
            session.setAttribute("password",password);
            Judge=1;
        }
        if(Judge==1){
            System.out.println("success");
            request.getRequestDispatcher("SignInIndex.jsp").forward(request, response);
        }
        else{
            request.getRequestDispatcher("pages/ForgetPassword.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
