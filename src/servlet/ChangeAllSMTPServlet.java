package servlet;

import dao.ParameterMgr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
//改变总的smtp状态
@WebServlet("/ChangeAllSMTPServlet")
public class ChangeAllSMTPServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        ParameterMgr pam=null;
        HttpSession session = request.getSession();
        String username = session.getAttribute("username").toString();
        String nickname = session.getAttribute("nickname").toString();
        String State = request.getParameter("smtps");

        session.setAttribute("username" ,username);
        session.setAttribute("nickname",nickname);
        try {
            pam=new ParameterMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            pam.setSmtpState(State);
            session.setAttribute("message","修改成功");
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("message","修改失败");
        }
        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }
}
