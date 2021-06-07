package android;

import dao.BlacklistAccountMgr;
import dao.ContactMgr;
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


@WebServlet("/AndroidSetFriendServlet")
public class AndroidSetFriendServlet extends HttpServlet {

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
        String account = json.getString("account");
        String []friendAccount = json.getString("friendAccount").split(";");

        ContactMgr contactMgr=null;
        UserMgr userMgr=null;

        try {
            contactMgr=new ContactMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            userMgr=new UserMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String rs="";

        try {
            for(String x:friendAccount)
                if(x!=null && x!="" && !x.equals("")) {
                    if(userMgr.getUser(x)!=null && userMgr.getUser(x).size()>0) {
                        contactMgr.addContact(account, x);
                        rs+="1";
                    }
                    else {
                        rs+="0";
                    }
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.write(rs);
        response.flushBuffer();

    }
}