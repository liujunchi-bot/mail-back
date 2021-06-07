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

@WebServlet("/ForbidServlet")
public class ForbidServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        HttpSession session = request.getSession();
        String username=session.getAttribute("username").toString();
        String nickname=session.getAttribute("nickname").toString();
        String forbidacc=request.getParameter("forbidaccount");

        session.setAttribute("username",username);
        session.setAttribute("nickname",nickname);
        UserMgr usermgr = null;
        try {
            usermgr =new UserMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            usermgr.changeUserRole(forbidacc,0);
            session.setAttribute("message","禁用成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        request.getRequestDispatcher("homepage.jsp").forward(request, response);
        response.getWriter().write("success");
        response.flushBuffer();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}