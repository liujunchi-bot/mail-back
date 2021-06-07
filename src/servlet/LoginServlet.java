package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.*;
import entity.User;
import dao.*;
@WebServlet( "/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("UTF-8");
//        System.out.println(System.getProperty("file.encoding"));
//        System.out.println(Charset.defaultCharset());
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        System.out.println(username+" "+password);
        UserMgr user = null;       //获取User管理类对象
        ArrayList <User> list = null;
        try {
            user = new UserMgr();
            list = user.getUser();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //创建session对象
        HttpSession session = request.getSession();
        int i;
        boolean isLogin = false;
        for(i=0;i<list.size();i++){
            String u = list.get(i).getAccount();
            String p = list.get(i).getPassword();
            boolean uIsRight = username.equals(u);
            boolean pIsRight = password.equals(p);
            System.out.println("test"+u+" "+p);
            System.out.println(username.equals(list.get(i).getAccount().toLowerCase()));
            System.out.println();
            System.out.println(uIsRight&&pIsRight);
            if(uIsRight&&pIsRight){
                // if(list.get(i).getRole()==1){
                //把用户数据保存在session域对象中
                //域名
                try {
                    String domain = new ParameterMgr().getDomainName();
                    session.setAttribute("username", username);
                    session.setAttribute("nickname",list.get(i).getNickname());
                    if(list.get(i).getRole()==2){
                        session.setAttribute("message","登录成功！");
                        isLogin = true;
                    }
                    else if(list.get(i).getRole()==1){
                        session.setAttribute("message","权限不足！");
                    }
                    else{
                        session.setAttribute("message","该账户已被禁用！");
                    }
                    break;
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
            else if(uIsRight){
                session.setAttribute("message","密码错误！");
                System.out.println("密码错误！");
                break;
            }
            else{
                session.setAttribute("message","请先注册！");
                System.out.println("请先注册！");
            }
        }

        if(isLogin){
            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        }
        else{
            request.getRequestDispatcher("SignInIndex.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
