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
2020/04/25
*/
//1;nickname=xxx：成功登录
//2：不存在该用户
//3：账号/密码错误

@WebServlet("/AndroidLoginServlet")
public class AndroidLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("进入login");
        response.setContentType("text/html");
        //response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        //设置编码格式
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(request.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        String str= URLDecoder.decode(sb.toString(),"UTF-8");
        JSONObject json = JSONObject.fromObject(str);
        System.out.println(json.toString());

        String username = json.getString("account");
        String password = json.getString("password");
//        String username ;
//        String password ;
//        username ="1";
//        password ="1";
        System.out.println(username+" "+password);
        ArrayList<User> list=null;
        try {
            //得到用户
            list=new UserMgr().getUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(list==null || list.size()==0) {
            //不存在该用户，登陆失败
            out.write("2");
            response.flushBuffer();
        } else {
            //存在该用户
            //接下来判断密码是否匹配
            User currUser=list.get(0);
            if(currUser.getPassword().equals(password)) {

                // 登陆成功
                out.write("1;nickname="+currUser.getNickname()+
                        ";role="+currUser.getRole());
                response.flushBuffer();
            } else {
                //不匹配，密码错误
                out.write("3");
                response.flushBuffer();
            }
        }
    }
}