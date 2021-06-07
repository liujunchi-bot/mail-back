package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import entity.*;
import dao.*;
import utils.logtool.CreateDocument;

import java.io.PrintWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //  response.setHeader();
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        //String username = request.getParameter("username");
        String username = request.getParameter("main-address");   //获取前端用户名
        String password = request.getParameter("newPassword");  //获取用户密码
        System.out.println(username);
//        int role     = Integer.valueOf(request.getParameter("role"));
        UserMgr user = null;
        try {
            user = new UserMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<User> list = null;
        try {
            list = user.getUser();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        int i=0;
        HttpSession session = request.getSession();
        int Judge=0;

        for(i=0;i<list.size();i++){
            if(list.get(i).getAccount().equals(username)){   //如果账号存在重复
                //返回一个错误信号到当前界面
                System.out.println("account exitence");
                session.setAttribute("message","该用户名已被注册！");

                Judge=1;
                break;
            }
            else if(i==list.size()-1){
                try {
                    user.addUser(username.toLowerCase(),username.toLowerCase(),password);//添加新用户到数据库再跳转到登录界面
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(username+ " "+ password);
                Judge =2 ;
                session.setAttribute("message","注册成功");
                CreateDocument.createDocument(username);
            }
        }
        if(Judge==1){
            System.out.println("regist fail");
            request.getRequestDispatcher("pages/registered.jsp").forward(request, response);
        }
        else if(Judge==2){
            request.getRequestDispatcher("SignInIndex.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    public static void main(String []args) throws SQLException, ClassNotFoundException {
        UserMgr user=null;
        user=new UserMgr();
        user.addUser("1@wemail.com","1@wemail.com","1@wemail.com");
    }
}

