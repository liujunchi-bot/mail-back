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

@WebServlet("/ChangeUserInfoServlet")
public class ChangeUserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        String username = request.getParameter("username");
        String e_password = request.getParameter("e_password");
        String e_again_password = request.getParameter("e_again_password");
        String nickname = request.getParameter("nickname");
        System.out.println(username + " " + nickname+ " "+ e_password + " " + e_again_password);
        HttpSession session = request.getSession();
        boolean ifSuccess = false;
        if(e_password.length()<6){
            session.setAttribute("message","密码长度要大于6位！");
        }
        else if(!e_password.equals(e_again_password)){
            session.setAttribute("message","密码输入不一致！");
        }
        else{
            UserMgr usermgr = null;
            try {
                usermgr = new UserMgr();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                boolean cp = usermgr.changePassword(username,e_password);
                boolean cn = usermgr.changeNickname(username,nickname);
                if(cp && cn){
                    ifSuccess = true;
                }
                else{
                    session.setAttribute("message","修改失败！");
                    if(username==null){
                        session.setAttribute("message","请先登录！");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        session.setAttribute("username",username);
        if(ifSuccess){
            session.setAttribute("nickname",nickname);
            session.setAttribute("message","修改成功！");
            request.getRequestDispatcher("homepage.jsp").forward(request,response);
        }
        else{
            request.getRequestDispatcher("pages/edituser.jsp").forward(request,response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
