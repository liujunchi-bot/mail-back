package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import dao.UserMgr;
@WebServlet( "/AddAccServlet")
public class AddAccServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        HttpSession session = request.getSession();
        String username = session.getAttribute("username").toString();
        String nickname = session.getAttribute("nickname").toString();

        String Account = request.getParameter("NAccount");
        String NickName = request.getParameter("NNickName");
        String PassWord = request.getParameter("NPassWord");
        String APassWord = request.getParameter("ANPassWord");


        session.setAttribute("username",username);
        session.setAttribute("nickname",nickname);
        boolean addSuccess = false;
        if(PassWord.length()<6){
            session.setAttribute("message","密码要求大于6位！");
        }
        else if(!PassWord.equals(APassWord)){
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
                usermgr.addUser(NickName,Account,PassWord);
                session.setAttribute("message","添加成功");
                addSuccess = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher("homepage.jsp").forward(request,response);
    }
}