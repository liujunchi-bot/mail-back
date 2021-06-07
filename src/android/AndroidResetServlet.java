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
import java.util.ArrayList;

/*
Qiao Qing
2020/04/24
*/

//修改密码的返回值：
//1：成功修改密码
//2：旧密码或者昵称不正确
//3：该用户不存在

@WebServlet("/AndroidResetServlet")
public class AndroidResetServlet extends HttpServlet {
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

        System.out.println(str);
        String username = json.getString("account");
        String nickname = json.getString("nickname");
        String [] password = json.getString("password").split("==");
        String oldPassword = password[0];
        String newPassword = password[1];

        UserMgr userMgr=null;
        try {
            userMgr=new UserMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //检查输入的用户名、昵称、密码是否正确作为验证
        ArrayList<User> list=null;
        try {
            list=userMgr.getUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(list!=null && list.size()!=0) {
            //存在此用户，接下来验证信息是否正确
            User currUser=list.get(0);
            if(currUser.getNickname().equals(nickname) && currUser.getPassword().equals(oldPassword)) {
                //验证信息正确，允许修改密码
                try {
                    userMgr.changePassword(username, newPassword);
                    out.write("1");
                    System.out.println("修改密码成功");
                    response.flushBuffer();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                out.write("2");
                response.flushBuffer();
            }

        } else {
            //不存在该用户，不允许修改密码
            out.write("3");
            response.flushBuffer();
        }
    }
}