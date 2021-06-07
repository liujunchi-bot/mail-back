package servlet;

import dao.ParameterMgr;
import entity.Parameter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

@WebServlet("/ChangeSizeServlet")
public class ChangeSizeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        HttpSession session = request.getSession();

        String Size = request.getParameter("msize");
        String username = session.getAttribute("username").toString();
        String nickname =session.getAttribute("nickname").toString();
        System.out.println(username+ " " + nickname);
        ParameterMgr p = null;
        try {
            p = new ParameterMgr();
            p.setUserSize(Integer.valueOf(Size));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        session.setAttribute("message","success");
        session.setAttribute("username",username);
        session.setAttribute("nickname",nickname);
        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }
}
