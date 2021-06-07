package servlet;

import dao.BlacklistAccountMgr;
import dao.BlacklistIpMgr;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/*
Qiao Qing
2020/04/06
*/

@WebServlet("/AddBlacklistServlet")
public class AddBlacklistServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");

        HttpSession session = request.getSession();
        String username = session.getAttribute("username").toString();
        String nickname = session.getAttribute("nickname").toString();
        session.setAttribute("username",username);
        session.setAttribute("nickname",nickname);

        String hostAccount=request.getParameter("hostAccount");
        String enemyAccount=request.getParameter("enemyAccount");
        String enemyIp=request.getParameter("enemyIp");
        int flagacc=0;
        int flagip=0;
        if(hostAccount==null || hostAccount=="") {
            response.getWriter().write("fail");
            response.flushBuffer();
        }
        if(enemyAccount!=null && enemyAccount!="") {
            try {
                new BlacklistAccountMgr().addBlacklistAccount(hostAccount,enemyAccount);
                flagacc++;
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(enemyIp!=null && enemyIp!="") {
            try {
                new BlacklistIpMgr().addBlacklistIp(hostAccount,enemyIp);
                flagip++;
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
//        response.getWriter().write("success");
//        response.flushBuffer();
        flagacc+=flagip;
        if(flagacc==0){
            session.setAttribute("message","至少填写一个过滤内容");
        }
        else{
            session.setAttribute("message","添加成功");
        }
        request.getRequestDispatcher("homepage.jsp").forward(request,response);
    }
}
