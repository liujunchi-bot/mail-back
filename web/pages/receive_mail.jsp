<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/25
  Time: 7:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/homepage.css" type="text/css"/>
    <title>WeMail</title>
    <script type="text/javascript" src="./js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="./js/home.js"></script>
    <script type="text/javascript" src="./js/cookie.js"></script>
    <script>

        var message="<%=session.getAttribute("message")%>";
        if(message != null){
            alert(message);
        }
        $(document).ready(function () {
            // setCookie('username', 'Hello@wemail.com',1);
            // var username = getCookie('username');
            // if (username === "" || username === null) {
            //     alert("请登录后查看！")
            //     window.location.href = "index.jsp";
            // }
        });
    </script>
</head>
<body>
<header class="g-hd head_pic">
    <img src="image/mail_logo.png" width="165px" height="60px"/>
    <div class=""><a class="help_style" href="">help</a></div>
    <div class=""><a class="quit_style" href="SignInIndex.html">quit</a></div>
</header>

<div class="g-sd">
    <form action="TransportServlet" target="_self" method="post">
        <input type="radio" id="title0" name="title" value="homepage.jsp" class="unselected m-flag" >系统设置</input>
        <input type="radio" id="title1" name="title" value="write_mail.jsp" class="unselected m-flag" >写信</input>
        <input type="radio" id="title2" name="title" value="rece_mail.jsp" class="selected m-flag" >收件箱</input>
        <input type="radio" id="title3" name="title" value="sent_mail.jsp" class="unselected m-flag" >已发送</input>
        <input type="radio" id="title4" name="title" value="user_mgr.jsp" class="unselected m-flag" >用户管理</input>
        <input type="radio" id="title5" name="title" value="blacklist.jsp" class="unselected m-flag" >黑名单</input>
        <input type="radio" id="title6" name="title" value="log_mgr.jsp" class="unselected m-flag" >日志管理</input>
    </form>
</div>
<div class="g-mn">
    <div id="con2" class="m-table l-con">
        <table>
            <thead>
            <tr>
                <th>状态</th>
                <th>发件人</th>
                <th>主题</th>
                <th>时间</th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
        <div class="scroll">
            <table>
                <tbody>
                <%
                    ArrayList<Mail> receiveList = new MailMgr().getReceivedMail(session.getAttribute("username").toString());
                    for(int i = 0; i < new Pager<Mail>().getCountPerPage(); i++){
                %>
                <tr>
                    <td>
                        <%
                            if()
                        %>
                        <img src="image/unlook.jpg" height="20px" width="25px">
                    </td>
                    <td>1756166171@qq.com</td>
                    <td>theme1</td>
                    <td>3月6日</td>
                    <td>
                        <input type="button" value="Check">
                        <input type="button" value="Delete">
                    </td>
                </tr>
                <%
                    }
                %>
                <tr>
                    <td>
                        <img src="image/unlook.jpg" height="20px" width="25px">
                    </td>
                    <td>1756166171@qq.com</td>
                    <td>theme1</td>
                    <td>3月6日</td>
                    <td>
                        <input type="button" value="Check">
                        <input type="button" value="Delete">
                    </td>
                </tr>
                <tr>
                    <td><img src="image/looked.jpg" height="20px" width="25px"></td>
                    <td>1756166171@qq.com</td>
                    <td>theme2</td>
                    <td>3月6日</td>
                    <td>
                        <input type="button" value="Check">
                        <input type="button" value="Delete">
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="image/unlook.jpg" height="20px" width="25px">
                    </td>
                    <td>1756166171@qq.com</td>
                    <td>theme1</td>
                    <td>3月6日</td>
                    <td>
                        <input type="button" value="Check">
                        <input type="button" value="Delete">
                    </td>
                </tr>
                <tr>
                    <td><img src="image/looked.jpg" height="20px" width="25px"></td>
                    <td>1756166171@qq.com</td>
                    <td>theme2</td>
                    <td>3月6日</td>
                    <td>
                        <input type="button" value="Check">
                        <input type="button" value="Delete">
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="image/unlook.jpg" height="20px" width="25px">
                    </td>
                    <td>1756166171@qq.com</td>
                    <td>theme1</td>
                    <td>3月6日</td>
                    <td>
                        <input type="button" value="Check">
                        <input type="button" value="Delete">
                    </td>
                </tr>
                <tr>
                    <td><img src="image/looked.jpg" height="20px" width="25px"></td>
                    <td>1756166171@qq.com</td>
                    <td>theme2</td>
                    <td>3月6日</td>
                    <td>
                        <input type="button" value="Check">
                        <input type="button" value="Delete">
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="image/unlook.jpg" height="20px" width="25px">
                    </td>
                    <td>1756166171@qq.com</td>
                    <td>theme1</td>
                    <td>3月6日</td>
                    <td>
                        <input type="button" value="Check">
                        <input type="button" value="Delete">
                    </td>
                </tr>
                <tr>
                    <td><img src="image/looked.jpg" height="20px" width="25px"></td>
                    <td>1756166171@qq.com</td>
                    <td>theme2</td>
                    <td>3月6日</td>
                    <td>
                        <input type="button" value="Check">
                        <input type="button" value="Delete">
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="image/unlook.jpg" height="20px" width="25px">
                    </td>
                    <td>1756166171@qq.com</td>
                    <td>theme1</td>
                    <td>3月6日</td>
                    <td>
                        <input type="button" value="Check">
                        <input type="button" value="Delete">
                    </td>
                </tr>
                <tr>
                    <td><img src="image/looked.jpg" height="20px" width="25px"></td>
                    <td>1756166171@qq.com</td>
                    <td>theme2</td>
                    <td>3月6日</td>
                    <td>
                        <input type="button" value="Check">
                        <input type="button" value="Delete">
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <nav class="m-page m-page-sr m-page-sm">
            <a href="#" class="pageprv z-dis">上一页</a>
            <a href="#">1</a>
            <a href="#" class="z-crt">2</a>
            <a href="#">3</a>
            <a href="#">4</a>
            <i>...</i>
            <a href="#">10</a>
            <a href="#" class="pagenxt">下一页</a>
        </nav>

    </div>

</div>
<div class="g-ft">

</div>
</body>
</html>
