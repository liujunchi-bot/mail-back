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

@WebServlet("/DeleteAccServlet")
public class DeleteAccServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        String Account = request.getParameter("keyID");
        System.out.println(Account);

        HttpSession session = request.getSession();
        String username =session.getAttribute("username").toString();
        String nickname= session.getAttribute("nickname").toString();
        session.setAttribute("username",username);
        session.setAttribute("nickname",nickname);



        UserMgr usermgr = null;
        try {
            usermgr = new UserMgr();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            boolean result = usermgr.delUser(Account);
            System.out.println(result);
            session.setAttribute("message","删除成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        request.getRequestDispatcher("homepage.jsp").forward(request,response);
        response.getWriter().write("success");
        response.flushBuffer();
    }

}