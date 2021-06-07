package android;

import dao.UserMgr;
import entity.User;
import net.sf.json.JSONObject;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
Qiao Qing
2020/04/24
*/
//1：注册成功
//2账号已存在

@WebServlet("/AndroidRegisterServlet")
public class AndroidRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("进入doPost");
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("进入register");

        response.setContentType("text/html");
        //设置编码格式
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
        String str=URLDecoder.decode(sb.toString(),"UTF-8");
        JSONObject json = JSONObject.fromObject(str);

        //获取用户信息
        String username = json.getString("account");
        String password = json.getString("password");
        String nickname = json.getString("nickname");

        ArrayList<User> list=null;
        UserMgr userMgr=null;

        try {
            userMgr=new UserMgr();
            try {
                list=userMgr.getUser(username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        if(list==null || list.size()==0) {
            //不存在该用户，可以注册
            try {
                userMgr.addUser(nickname,username,password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //注册成功
            out.write("1");
            response.flushBuffer();
        } else {
            //存在该用户，即已注册
            out.write("2");
            response.flushBuffer();
        }
    }
}