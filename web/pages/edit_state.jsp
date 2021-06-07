<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>编辑用户-Wemail</title>
    <link rel="stylesheet" href="../css/add_user.css"/>
    <meta name="author" content="sgafei">
    <script>
        $(document).ready(function () {
            var message="<%=session.getAttribute("message")%>";
            if(message != null){
                alert(message);
            }
        });
    </script>
</head>
<body>
<div class="a-hd"></div>
<div class="a-sd"></div>
<div class="a-mn">
    <fieldset>
        <legend>
            编辑用户
        </legend>
        <input type="text" name="username" value="<%=session.getAttribute("username")%>" hidden>
        <input type="text" name="nickname" value="<%=session.getAttribute("nickname")%>" hidden>
        <form action="../ChangeStateServlet" target="_self" method="post">
            <p>
                昵称<input type="text" name="cnickname" id="cnickname" value="${enickname}" class="inputtxt" placeholder="请输入新昵称" required="required">
            </p>
            <p>
                账户<%=session.getAttribute("eusername")%>
                <input type="text" name="cusername" id="cusername" value="${eusername}" class="inputtxt" hidden>
            </p>
            <p>
                密码<input type="password" name="cpassword" id="cpassword" value="${epassword}"  class="inputtxt" placeholder="请输入新密码" required="required">
            </p>
            <p>
                ROLE<input type="number" name="crole" id="crole" value="${erole}"  class="inputtxt" placeholder="请输入权限" required="required">
            </p>
            <p>
                SMTP<input type="number" name="csmtp" id="csmtp" value="${esmtp}"  class="inputtxt" placeholder="请输入SMTP状态" required="required">
            </p>
            <p>
                POP3<input type="number" name="cpop3" id="cpop3" value="${epop3}"  class="inputtxt" placeholder="请输入POP3状态" required="required">
            </p>

            <p>
                <input type="submit" class="inputbtn" value="提交">
            </p>
        </form>
        <form action="../GoHomeServlet" target="_self" method="post">
            <p>
                <input type="submit" class="inputbtn" value="返回">
            </p>
        </form>
    </fieldset>
</div>
<div class="a-ft"></div>
</body>
</html>