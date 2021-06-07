package servlet;

import dao.MailMgr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet( "/DeleteMailServlet")
public class DeleteMailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        HttpSession session=request.getSession();
        String mail_id=request.getParameter("keyID");
        String username=request.getParameter("username");
        String nickname=request.getParameter("nickname");
        MailMgr m=null;
        try {
            m=new MailMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            m.delMail((long)Integer.valueOf(mail_id));
            session.setAttribute("message","success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        session.setAttribute("username",username);
        session.setAttribute("nickname",nickname);
//        request.getRequestDispatcher("homepage.jsp").forward(request, response);
        response.getWriter().write("success");
        response.flushBuffer();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}