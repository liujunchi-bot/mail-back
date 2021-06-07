package servlet;

import dao.BlacklistAccountMgr;
import dao.BlacklistIpMgr;
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

/*
Qiao Qing
2020/04/06
*/

@WebServlet("/DelBlackListServlet")
public class DelBlackListServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");

        HttpSession session = request.getSession();
        String username=request.getParameter("username");
        String nickname=request.getParameter("nickname");
        session.setAttribute("username",username);
        session.setAttribute("nickname",nickname);

        String hostAccount=request.getParameter("hostAccount");
        String enemy=request.getParameter("enemy");
        System.out.println(hostAccount+ " "+enemy);
        if(enemy.contains("@")){
            try {
                new BlacklistIpMgr().delBlacklistIp(hostAccount,enemy);
                session.setAttribute("message","删除成功！");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else  {
            try {
                new BlacklistAccountMgr().delBlacklistAccount(hostAccount,enemy);
                session.setAttribute("message","删除成功！");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        response.getWriter().write("success");
        response.flushBuffer();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
