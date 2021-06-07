package servlet;

import dao.UserMgr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ChangeStateServlet")
public class ChangeStateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        HttpSession session = request.getSession();

        String username=session.getAttribute("username").toString();
        String nickname=session.getAttribute("nickname").toString();

        String Account = request.getParameter("cusername").toString();
        String Password = request.getParameter("cpassword").toString();
        String NickName = request.getParameter("cnickname").toString();
        String Smtpstate = request.getParameter("csmtp").toString();
        String role =request.getParameter("crole").toString();
        String popstate =request.getParameter("cpop3").toString();

        session.setAttribute("username",username);
        session.setAttribute("nickname",nickname);

        System.out.println(role);

        UserMgr usermgr = null;
        try {
            usermgr = new UserMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            usermgr.changePassword(Account,Password);
            usermgr.changeNickname(Account,NickName);
            usermgr.changePop3state(Account,Integer.valueOf(popstate));
            usermgr.changeUserRole(Account,Integer.valueOf(role));
            usermgr.changeStmpstate(Account,Integer.valueOf(Smtpstate));
            session.setAttribute("message","修改成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}