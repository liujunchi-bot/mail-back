<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册</title>
    <link rel="stylesheet" href="./css/initialization.css">
    <link rel="stylesheet" href="./css/registerCss.css">
    <link rel="stylesheet" href="./css/HeaderAndFooter.css">
    <script type="text/javascript">
        var message="<%=session.getAttribute("message")%>";
        if(message != null){
            alert(message);
        }
    </script>
</head>
<body>
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
                <h2>欢迎注册WeMail邮箱</h2>
                <a href="./SignInIndex.html">返回登录</a>
            </div>
            <div class="m-body">
                <form id="loginForm" autocomplete="off" name="loginForm" action="RegisterServlet" method="post" target="_self" onsubmit="return checkPassword()">
                    <input type="text" placeholder="邮箱地址" name="main-address" id="main-address" autocomplete="off" required="required"/>
                    <p id="emailPrompt"></p>
                    <input class="password" type="password" placeholder="密码" id="newPassword" name="newPassword" autocomplete="new-password" οnkeydοwn="if(event.keyCode==32) return false" required="required"/>
                    <p id="newPasswordPrompt"></p>
                    <input class="password" type="password" placeholder="请再输入一遍密码" id="againPassword" autocomplete="new-password" οnkeydοwn="if(event.keyCode==32) return false" required="required"/>
                    <p id="againPasswordPrompt"></p>
                    <input type="submit" value="立即注册" class="reg-btw" id="SureBtw">
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