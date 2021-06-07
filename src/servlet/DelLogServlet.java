package servlet;

import utils.logtool.DeleteLog;
import utils.logtool.FileHanding;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/DelLogServlet")
public class DelLogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        String order = request.getParameter("order");
        System.out.println("order"+order);

        HttpSession session = request.getSession();
        String username =session.getAttribute("username").toString();
        String nickname= session.getAttribute("nickname").toString();
        session.setAttribute("username",username);
        session.setAttribute("nickname",nickname);

        List<String> filePath = FileHanding.findFilePaths(FileHanding.basepath, new ArrayList<>());
        if(DeleteLog.delete(filePath.get(Integer.valueOf(order)-1)))
            response.getWriter().write("success");
        response.flushBuffer();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
