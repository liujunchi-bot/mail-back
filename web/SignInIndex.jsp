<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录界面</title>
    <link rel="stylesheet" href="css/initialization.css">
    <link rel="stylesheet" href="css/SignIn.css">
    <link rel="stylesheet" href="css/HeaderAndFooter.css">

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
            <img src="image/mail_logo.png" alt="邮箱图标"/>
        </div>
    </div>

    <!-- 中间部分-->
    <div class="middle">
        <!-- 左部分-->
        <div class="middle-left">
            <div class="login-pictures-title">WeMail,常联系！</div>
            <p>1982年，第一张电脑笑脸诞生</p>
            <p>今天，人们已经习惯用它来表达心情</p>
            <p>现在，您也可以在邮件里</p>
            <p>用:-)来传达一个微笑的表情</p>
        </div>
        <!-- 中间部分-->
        <div class="middle-middle">
            <img src="image/login_picture.png" alt="笑脸图片"/>
        </div>
        <!-- 右部分-->
        <div class="middle-right">
            <div class="form-title">登录</div>
            <form id="loginForm" autocomplete="off" name="loginForm" action="LoginServlet" method="post" target="_self">
                <input type="text"class="input1 username" name="username" placeholder="请输入账号登录" required="required"/>
                <input type="password" class="input1 passWord" name="password" placeholder="密码" required="required"/>
                <input type="submit" value="登录"   class="login-button"  value="login" ></submit>
            </form>
            <div class="forget">
                <a href="pages/registered.html" class="link">注册账号</a><!--注册账号-->
                <a href="pages/ForgetPassword.html" class="link">忘了密码?</a><!--忘了密码-->
            </div>
        </div>
    </div>

    <!-- 底部分-->
    <div class="footer">
        <a href="#" class="link">帮助中心</a>&nbsp;&nbsp;|&nbsp;&nbsp;WeMail小组版权所有&copy; 2020
    </div>
</body>
</html>