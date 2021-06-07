<%@ page import="java.sql.SQLException" %>
<%@ page import="utils.Pager" %>
<%@ page import="entity.Mail" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entity.User" %>
<%@ page import="entity.BlacklistAccount" %>
<%@ page import="entity.BlacklistIp" %>
<%@ page import="dao.*" %>
<%@ page import="java.util.List" %>
<%@ page import="utils.logtool.FileHanding" %>
<%@ page import="utils.logtool.DeleteLog" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/homepage.css" type="text/css"/>
    <title>WeMail</title>
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="js/home.js"></script>
    	<script type="text/javascript" src="js/cookie.js"></script>
    <script>

        function getBasePath(){
            return '<%=basePath%>';
        }

        var message="<%=session.getAttribute("message")%>";
        if(message != null){
            alert(message);
        }
        $(document).ready(function () {
            setCookie('username', "<%=session.getAttribute("username")%>",1);
            setCookie('nickname', "<%=session.getAttribute("nickname")%>",1);
            var username = getCookie('username');
            var nickname = getCookie('nickname');
            $("#nickname").attr("value",nickname);

            $("#username").attr("value",username);
            if (username === "" || username === null) {
                alert("请登录后查看！")
                window.location.href = "index.jsp";
            }
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

    <div id="title0" class="selected m-flag" onclick='opens(0)'>系统设置</div>
    <div id="title1" class="unselected m-flag" onclick='opens(1)'>写信</div>
    <div id="title2" class="unselected m-flag" onclick='opens(2)'>收件箱</div>
    <div id="title3" class="unselected m-flag" onclick='opens(3)'>已发送</div>
    <div id="title4" class="unselected m-flag" onclick='opens(4)'>用户管理</div>
    <div id="title5" class="unselected m-flag" onclick='opens(5)'>黑名单</div>
    <div id="title6" class="unselected m-flag" onclick='opens(6)'>日志管理</div>
</div>
<div class="g-mn">
    <div id="con0" class="l-con" style="display: block;">
        <fieldset>
            <legend>用户信息</legend>
            <%
//                request.setAttribute("nickname","Hello");
//                request.setAttribute("username","Hello@wemail.com");
            %>
            <form action="pages/edituser.jsp" method="post" target="_self">
                <div class="c-nick">
                    昵称:
                    <input type="text" value="<%=session.getAttribute("nickname")%>" name="Nickname" class="no-input-bg" disabled>
                </div>
                <input type="submit" id="editu" value="编辑">

                <div class="c-mail">
                    用户名：
                    <input id="s-account" type="text" value="<%=session.getAttribute("username")%>" name="Username" class="no-input-bg" disabled></div>
                <div class="c-ps">密码：******</div>
            </form>
        </fieldset>
        <fieldset>
            <legend>邮箱容量</legend>
            <div class="c-msize">
                <form action="ChangeSizeServlet" method="post" target="_self">
                普通用户：
                <div id="msize-c" class="input_msize input_msize_b">
                    <input id="msize" name="msize" type="text" class="input_msize" value="<%=new ParameterMgr().getUserSize()%>">
                </div>&nbsp;Byte &nbsp;&nbsp;&nbsp;
                <input id="msize_but" type="submit" class="c-msize-but" value="修改">
                </form>
            </div>
        </fieldset>


        <fieldset>
            <legend>协议控制</legend>
            <table class="server-table">
                <tr>
                    <td>SMTP协议状态</td>
                    <form action="ChangeAllSMTPServlet" method="post" target="_self">
                    <td>
                        <div class="input_msize input_msize_b">
                            <input id="smtps" name="smtps" type="text" class="input_msize" value="<%=new ParameterMgr().getSmtpState()%>">
                        </div>
                    </td>
                    <td>
                        <input type="submit" class="c-msize-but" value="修改">
                    </td>
                    </form>
                    <td>PORT:</td>
                    <form action="ChangeAllSMTPPortServlet" method="post" target="_self">
                        <td>
                            <div class="input_msize input_msize_b">
                                <input id="smtpsport" name="smtpsport" type="text" class="input_msize" value="<%=new ParameterMgr().getSmtpPort()%>">
                            </div>
                        </td>
                        <td>
                            <input type="submit" class="c-msize-but" value="修改">
                        </td>
                    </form>
                </tr>
                <tr>
                    <td>POP3协议状态</td>
                    <form action="ChangeAllPOP3Servlet" method="post" target="_self">
                        <td>
                            <div class="input_msize input_msize_b">
                                <input id="pop3s" name="pop3s" type="text" class="input_msize" value="<%=new ParameterMgr().getPop3State()%>">
                            </div>
                        </td>
                        <td>
                            <input type="submit" class="c-msize-but" value="修改">
                        </td>
                    </form>
                    <td>PORT:</td>
                    <form action="ChangeAllPOP3PortServlet" method="post" target="_self">
                        <td>
                            <div class="input_msize input_msize_b">
                                <input id="pop3sport" name="pop3sport" type="text" class="input_msize" value="<%=new ParameterMgr().getPop3Port()%>">
                            </div>
                        </td>
                        <td>
                            <input type="submit" class="c-msize-but" value="修改">
                        </td>
                    </form>
                </tr>
                <tr>
                    <td>域名设置</td>
                    <form action="ChangeDomainServlet" method="post" target="_self">
                        <td>
                            <div class="input_msize input_msize_b">
                                <input id="domain" name="domain" type="text" class="input_msize" value="<%=new ParameterMgr().getDomainName()%>">
                            </div>
                        </td>
                        <td>
                            <input type="submit" class="c-msize-but" value="修改">
                        </td>
                    </form>
                </tr>
            </table>
        </fieldset>
    </div>
    <div id="con1" class="l-con table_style" style="display: none;">
        <fieldset>
            <legend>写信</legend>
            <form action="SendMailServlet" target="_self" method="post">
                <table width="100%">
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="Send">
                            <input type="button" value="Close" onclick='opens(0)'>
                            <input type="email" value="${username}" placeholder="发件人" id="send_p" name="send-p" required="required">
                            <input type="password" placeholder="请输入密码" id="ps-p" name="ps-p" required="required">
                        </td>
                    </tr>
                    <tr>
                        <td width="80px">收件人</td>
                        <td>
                            <input type="text" id="rece_p" name="rece_p" class="send-mail" required="required">
                        </td>
                    </tr>
                    <tr>
                        <td>主题</td>
                        <td>
                            <input type="text" id="theme" name="theme" class="send-mail" required="required">
                        </td>
                    </tr>
                    <tr>
                        <td>正文</td>
                        <td>
                            <textarea id="content" name="content" class="send-mail" cols="30" rows="10"
                                      required="required"></textarea>
                        </td>
                    </tr>
                </table>
            </form>
        </fieldset>
    </div>
    <div id="con2" class="m-table l-con" style="display: none;">
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
            <table id="receiveTable">
                <tbody>

                <%
                    ArrayList<Mail> receiveList = null;
                    try {
                        receiveList = new MailMgr().getReceivedMail();
                        System.out.println(receiveList.size());
                        for(int i = 0; i < receiveList.size(); i++){
                %>
                <tr>
                    <td>
                        <%if(receiveList.get(i).getMailState()==2){%>
                        <img src="image/looked.jpg" height="20px" width="25px">
                        <%}else{%>
                        <img src="image/unlook.jpg" height="20px" width="25px">
                        <%}
                        %>
                    </td>
                    <td><%=receiveList.get(i).getSenderAccount()%></td>
                    <td><%=receiveList.get(i).getSubject()%></td>
                    <td><%=receiveList.get(i).getDateTime()%></td>
                    <td>
                        <input type="button" value="Check" onclick="checkMail(<%=receiveList.get(i).getMailId()%>)">
                        <input type="button" value="Delete" onclick="deleteMail(this,<%=receiveList.get(i).getMailId()%>,'receiveTable')">
                    </td>
                </tr>
                <% } } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                %>
                </tbody>
            </table>
        </div>
<%--        <nav class="m-page m-page-sr m-page-sm">--%>
<%--            <a href="#" class="pageprv z-dis">上一页</a>--%>
<%--            <a href="#">1</a>--%>
<%--            <a href="#" class="z-crt">2</a>--%>
<%--            <a href="#">3</a>--%>
<%--            <a href="#">4</a>--%>
<%--            <i>...</i>--%>
<%--            <a href="#">10</a>--%>
<%--            <a href="#" class="pagenxt">下一页</a>--%>
<%--        </nav>--%>

    </div>
    <div id="con3" class="m-table l-con" style="display: none;">
        <table>
            <thead>
            <tr>
                <th>状态</th>
                <th>收件人</th>
                <th>主题</th>
                <th>时间</th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
        <div class="scroll">
            <table id="sendTable">
                <tbody>
                <%
                    ArrayList<Mail> sendList = null;
                    try {
                        sendList = new MailMgr().getsendedMail();
                        for(int i = 0; i < sendList.size(); i++){
                %>
                <tr>
                    <td>
                        <%if(sendList.get(i).getMailState()>0){%>
                        <img src="image/sent.png" height="20px" width="25px">
                        <%}else{%>
                        <img src="image/resend.png" height="20px" width="25px">
                        <%}
                        %>
                    </td>
                    <td><%=sendList.get(i).getReceiverAccount()%></td>
                    <td><%=sendList.get(i).getSubject()%></td>
                    <td><%=sendList.get(i).getDateTime()%></td>
                    <td>
                        <input type="button" value="Check" onclick="checkMail(<%=sendList.get(i).getMailId()%>)">
                        <input type="button" value="Delete" onclick="deleteMail(this,<%=receiveList.get(i).getMailId()%>,'sendTable')">
                    </td>
                </tr>
                <%}
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                %>
                </tbody>
            </table>
        </div>
<%--        <nav class="m-page m-page-sr m-page-sm">--%>
<%--            <a href="#" class="pageprv z-dis">上一页</a>--%>
<%--            <a href="#">1</a>--%>
<%--            <a href="#" class="z-crt">2</a>--%>
<%--            <a href="#">3</a>--%>
<%--            <a href="#">4</a>--%>
<%--            <i>...</i>--%>
<%--            <a href="#">10</a>--%>
<%--            <a href="#" class="pagenxt">下一页</a>--%>
<%--        </nav>--%>

    </div>
    <div id="con4" class="l-con user-table" style="display: none;">
<%--        <div class="add-u-but">--%>
            <form action="GoHomeServlet" target="_self" method="post">
                <input type="text" name="username" value="<%=session.getAttribute("username")%>" hidden>
                <input type="text" name="nickname" value="<%=session.getAttribute("nickname")%>" hidden>
                <input type="text" name="relocation" value="pages/add_user.jsp" hidden>
                <input type="submit" value="添加用户">
            </form>
<%--        </div>--%>
        <table>
            <thead>
            <tr>
                <th>昵称</th>
                <th>邮箱</th>
                <th>密码</th>
                <th>权限</th>
                <th>SMTP</th>
                <th>POP3</th>
                <th>邮箱已用量</th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
        <div class="scroll">
            <table id="userTable">
                <tbody>
                <%
                    ArrayList<User> userList = null;
                    try {
                        userList = new UserMgr().getUser();
                        for(int i = 0; i < userList.size(); i++){
                %>
                <tr>
                    <td><%=userList.get(i).getNickname()%></td>
                    <td><%=userList.get(i).getAccount()%></td>
                    <td><%=userList.get(i).getPassword()%></td>
                    <td><%=userList.get(i).getRole()%></td>
                    <td><%=userList.get(i).getSmtpstate()%></td>
                    <td><%=userList.get(i).getPop3State()%></td>
                    <td><%=userList.get(i).getUsedsize()%></td>
                    <td>
                        <input type="button" value="Edit" onclick="editState(<%=userList.get(i).getAccount()%>)">
                        <input type="button" value="Disable" onclick="forbidUser(<%=userList.get(i).getAccount()%>)">
                        <input type="button" value="Delete" onclick="deleteUser(this,<%=userList.get(i).getAccount()%>)">
                    </td>
                </tr>
                <%}
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                %>
                </tbody>
            </table>
        </div>
<%--        <nav class="m-page m-page-sr m-page-sm">--%>
<%--            <a href="#" class="pageprv z-dis">上一页</a>--%>
<%--            <a href="#">1</a>--%>
<%--            <a href="#" class="z-crt">2</a>--%>
<%--            <a href="#">3</a>--%>
<%--            <a href="#">4</a>--%>
<%--            <i>...</i>--%>
<%--            <a href="#">10</a>--%>
<%--            <a href="#" class="pagenxt">下一页</a>--%>
<%--        </nav>--%>
    </div>
    <div id="con5" class="l-con" style="display: none;">
        <form action="GoHomeServlet" target="_self" method="post">
            <input type="text" name="username" value="<%=session.getAttribute("username")%>" hidden>
            <input type="text" name="nickname" value="<%=session.getAttribute("nickname")%>" hidden>
            <input type="text" name="relocation" value="pages/add_fliter.jsp" hidden>
            <input type="submit" value="添加过滤">
        </form>
<%--        <div class="add-u-but"><a href="pages/add_fliter.jsp" target="_blank">添加过滤</a></div>--%>
        <div class="b-account">
            <table>
                <thead>
                <tr>
                    <th>邮箱号</th>
                    <th>黑名单邮箱号</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
            <div class="scroll">
                <table id="accTable">
                    <tbody>
                    <%
                        ArrayList<BlacklistAccount> blackAccountList = null;
                        try {
                            blackAccountList = new BlacklistAccountMgr().getBlacklistAccount();
                            for(int i = 0; i < blackAccountList.size(); i++){
                    %>
                    <tr>
                        <td><%=blackAccountList.get(i).getHostAccount()%></td>
                        <td><%=blackAccountList.get(i).getEnemyAccount()%></td>
                        <td>
                            <input type="button" value="Delete" onclick="deleteBlackList(this,<%=blackAccountList.get(i).getHostAccount()%>,'accTable',<%=blackAccountList.get(i).getEnemyAccount()%>)">
                        </td>
                    </tr>
                    <%}
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="b-account">
<%--            <nav class="m-page m-page-sr m-page-sm">--%>
<%--                <a href="#" class="pageprv z-dis">上一页</a>--%>
<%--                <a href="#">1</a>--%>
<%--                <a href="#" class="z-crt">2</a>--%>
<%--                <a href="#">3</a>--%>
<%--                <a href="#">4</a>--%>
<%--                <i>...</i>--%>
<%--                <a href="#">10</a>--%>
<%--                <a href="#" class="pagenxt">下一页</a>--%>
<%--            </nav>--%>
        </div>
        <div class="b-ip">
            <table>
                <thead>
                <tr>
                    <th>邮箱号</th>
                    <th>黑名单域名</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
            <div class="scroll">
                <table id="ipTable">
                    <tbody>
                    <%
                        ArrayList<BlacklistIp> blackIpList = null;
                        try {
                            blackIpList = new BlacklistIpMgr().getBlacklistIp();
                            for(int i = 0; i < blackIpList.size(); i++){
                    %>
                    <tr>
                        <td><%=blackIpList.get(i).getHostAccount()%></td>
                        <td><%=blackIpList.get(i).getEnemyIp()%></td>
                        <td>
                            <input type="button" value="Delete" onclick="deleteBlackList(this,<%=blackIpList.get(i).getHostAccount()%>,'ipTable',<%=blackIpList.get(i).getEnemyIp()%>)">
                        </td>
                    </tr>
                    <%}
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="b-ip">
<%--            <nav class="m-page m-page-sr m-page-sm">--%>
<%--                <a href="#" class="pageprv z-dis">上一页</a>--%>
<%--                <a href="#">1</a>--%>
<%--                <a href="#" class="z-crt">2</a>--%>
<%--                <a href="#">3</a>--%>
<%--                <a href="#">4</a>--%>
<%--                <i>...</i>--%>
<%--                <a href="#">10</a>--%>
<%--                <a href="#" class="pagenxt">下一页</a>--%>
<%--            </nav>--%>
        </div>
    </div>
    <div id="con6" class="l-con" style="display: none;">
        <%
            List<String> fileName = FileHanding.getFileNames();
            List<String> filePath = FileHanding.getFilePaths(FileHanding.basepath, new ArrayList<>());
            int fileNum = fileName.size();
        %>
        <div class="l-log">
            <table >
                <thead>
                <tr>
                    <th>序号</th>
                    <th>文件名</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
            <div class="scroll">
                <table id="logTable">
                    <tbody>
                        <%
                            int i = 0;
                            for(String str : filePath){
                                i++;
                        %>
                        <tr>
                            <td>
                                <%=i%>
                            </td>
                            <td><a href="<%=str %>"><%=str %></a></td>
                            <td>
                                <input type="button" value="Delete" onclick="deleteLog(<%=i%>,this)">
                            </td>
                        </tr>
                        <%
                            }
                        %>


                    </tbody>
                </table>
            </div>
        </div>
<%--        <nav class="m-page m-page-sr m-page-sm">--%>
<%--            <a href="#" class="pageprv z-dis">上一页</a>--%>
<%--            <a href="#">1</a>--%>
<%--            <a href="#" class="z-crt">2</a>--%>
<%--            <a href="#">3</a>--%>
<%--            <a href="#">4</a>--%>
<%--            <i>...</i>--%>
<%--            <a href="#">10</a>--%>
<%--            <a href="#" class="pagenxt">下一页</a>--%>
<%--        </nav>--%>

    </div>
</div>
<div class="g-ft">

</div>

</body>
<script type="text/javascript">
    function deleteMail(obj1,obj2,obj3) {
        var keyID=obj2;
        var tablename=obj3;
        var rowNumber=obj1.parentNode.parentNode.rowIndex;
        if(confirm('确定删除吗？')){
            $.ajax({
                url: 'DeleteMailServlet',
                type : "post",
                data : {
                    "keyID" : keyID
                },
                success: function (result) {
                    if(result==="success"){
                        alert("删除成功");
                        document.getElementById(tablename).deleteRow(rowNumber);
                        // location.reload();
                    } else {
                        alert("删除失败");
                    }
                }
            });
            return true;
        }
        return false;
    }
    function checkMail(obj2) {
        var keyID=obj2;
            $.ajax({
                url: 'ReadMailServlet',
                type : "post",
                data : {
                    "keyID" : keyID
                },
                success: function (result) {
                    if(result==="success"){
                        window.location.href = "pages/check_email.jsp";
                        // location.reload();
                    } else {
                        alert("查看失败");
                    }
                }
            });
    }
    function deleteUser(obj1,obj2) {
        var keyID=obj2;
        var rowNumber=obj1.parentNode.parentNode.rowIndex;
        if(confirm('确定删除吗？')){
            $.ajax({
                url: 'DeleteAccServlet',
                type : "post",
                data : {
                    "keyID" : keyID
                },
                success: function (result) {
                    if(result==="success"){
                        alert("删除成功");
                        document.getElementById("userTable").deleteRow(rowNumber);
                        // location.reload();
                    } else {
                        alert("删除失败");
                    }
                }
            });
            return true;
        }
        return false;
    }
    function forbidUser(obj2) {
        var keyID=obj2;
        $.ajax({
            url: 'ForbidServlet',
            type : "post",
            data : {
                "forbidaccount" : keyID
            },
            success: function (result) {
                if(result==="success"){
                    window.location.href = "homepage.jsp";
                    // location.reload();
                } else {
                    alert("修改失败");
                }
            }
        });
    }
    function editState(obj2) {
        var keyID=obj2;
        $.ajax({
            url: 'GoHomeServlet',
            type : "post",
            data : {
                "relocation" : "pages/edit_state.jsp",
                "account":keyID
            },
            success: function (result) {
                if(result==="success"){
                    window.location.href = "pages/edit_state.jsp";
                    alert("查看成功");
                } else {
                    alert("查看失败");
                }
            }
        });
    }
    function deleteBlackList(obj1,obj2,obj3,obj4) {
        var hostAccount=obj2;
        var enemy=obj4;
        var tablename=obj3;
        var rowNumber=obj1.parentNode.parentNode.rowIndex;
            $.ajax({
                url: 'DelBlackListServlet',
                type : "post",
                data : {
                    "hostAccount" : hostAccount,
                    "enemy": enemy
                },
                success: function (result) {
                    if(result=="success"){
                        alert("删除成功");
                        document.getElementById(tablename).deleteRow(rowNumber);
                        // location.reload();
                    } else {
                        alert("删除失败");
                    }
                }
            });
    }
    function deleteLog(i,row){
        var rowNumber=row.parentNode.parentNode.rowIndex;
        var order = i;
            $.ajax({
                url: 'DelLogServlet',
                type : "post",
                data : {
                    "order" : order
                },
                success: function (result) {
                    if(result==="success"){
                        alert("删除成功");
                        document.getElementById('logTable').deleteRow(rowNumber);
                        // location.reload();
                    } else {
                        alert("删除失败");
                    }
                }
            });
    }
</script>
</html>