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

@WebServlet("/GoHomeServlet")
public class GoHomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        HttpSession session = request.getSession();
        String location = request.getParameter("relocation");
        System.out.println(location);
        String username = session.getAttribute("username").toString();//获取账户信息，获得发件箱。
        String nickname = session.getAttribute("nickname").toString();
        session.setAttribute("username",username);
        session.setAttribute("nickname",nickname);

        session.setAttribute("message","跳转成功");

        if(location==null){
            request.getRequestDispatcher("homepage.jsp").forward(request,response);
        }
        else if(location.equals("pages/edit_state.jsp")){
            String account = request.getParameter("account");
            try {
                UserMgr userMgr = new UserMgr();
                ArrayList<User> ulist = userMgr.getUser(account);
                System.out.println(ulist.get(0).toString());
                session.setAttribute("enickname", ulist.get(0).getNickname());
                session.setAttribute("eusername", account);
                session.setAttribute("epassword", ulist.get(0).getPassword());
                session.setAttribute("erole", ulist.get(0).getRole());
                session.setAttribute("esmtp", ulist.get(0).getSmtpstate());
                session.setAttribute("epop3", ulist.get(0).getPop3State());

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
//            request.getRequestDispatcher(location).forward(request,response);
            response.getWriter().write("success");
            response.flushBuffer();
        }
        else{
            request.getRequestDispatcher(location).forward(request,response);
        }
    }
}