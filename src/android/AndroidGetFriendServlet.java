package android;

import dao.ContactMgr;
import dao.MailMgr;
import entity.Contact;
import entity.Mail;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utils.MailHelp;

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
import java.util.ArrayList;


@WebServlet("/AndroidGetFriendServlet")
public class AndroidGetFriendServlet extends HttpServlet {

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
        JSONObject jsonObject = JSONObject.fromObject(str);
        String account=jsonObject.getString("account");
        System.out.println(str);

        ContactMgr contactMgr=null;
        try {
            contactMgr=new ContactMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Contact> list= null;
        try {
            list = contactMgr.getContact(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray=new JSONArray();
        for(Contact contact:list) {

            /*
            private long conId;
            private String hostAccount;
            private String friendAccount;
             */


            String conId=""+contact.getConId();
            String hostAccount=contact.getHostAccount();
            String friendAccount=contact.getFriendAccount();

            JSONObject obj =new JSONObject();
            //obj.put("conId",conId);
            //obj.put("hostAccount",hostAccount);
            obj.put("friendAccount",friendAccount);
            jsonArray.add(obj);

        }
        out.write(jsonArray.toString());
        response.flushBuffer();
    }
}
