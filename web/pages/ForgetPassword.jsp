<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>忘记密码</title>
    <link rel="stylesheet" href="./css/initialization.css">
    <link rel="stylesheet" href="./css/ForgetPassword.css">
    <link rel="stylesheet" href="./css/HeaderAndFooter.css">
</head>
<body>
<script>

    var message=null;
    message="<%=session.getAttribute("message")%>";
    if(message!=null){
        alert(message);
    }
</script>
    <!--头部-->
    <div class="header">
        <div class="img-logo">
            <img src="./image/mail_logo.png" alt="邮箱图标"/>
        </div>
    </div>

     <!--中间部分的容器-->
    <div class="register">
        <div class="main-body">
            <div class="title">
                <h2>重置密码</h2>
                <a href="./SignInIndex.html#">返回登录</a>
            </div>
            <div class="m-body">
                <form id="ForgetForm" autocomplete="off" name="ForgetForm" action="./FindPasswordServlet" method="post" target="_self" onsubmit="return checkPassword()">
                    <input type="text"  placeholder="请输入邮箱账号" id="main-address" name="main-address" autocomplete="off"οnkeydοwn="if(event.keyCode==32) return false" required="required"/>
                    <input type="text" placeholder="请输入昵称(默认账号)" id="nickname" name="nickname" οnkeydοwn="if(event.keyCode==32) return false" required="required" autocomplete="off"/>
                    <input  id="newPassword" name="newPassword"  class="password" type="password" placeholder="请输入重置密码" autocomplete="new-password" οnkeydοwn="if(event.keyCode==32) return false" required="required"/>
                    <div id="newPasswordPrompt"></div>
                    <input  id="againPassword" class="password" type="password" placeholder="请再输入一遍密码" autocomplete="new-password" οnkeydοwn="if(event.keyCode==32) return false" required="required"/>
                    <div id="againPasswordPrompt"></div>
                    <button id="SureBtw" class="forget-btw">重置密码</button>
                </form>
            </div>
        </div>
    </div>

    <!-- 底部分-->
    <div class="footer">
        <a href="#" class="link">帮助中心</a>&nbsp;&nbsp;|&nbsp;&nbsp;WeMail小组版权所有&copy; 2020
    </div>
    <script src="./js/ForgetPassword.js" type="text/javascript"></script>

</body>
</html>