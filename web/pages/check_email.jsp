<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>查看邮件-Wemail</title>
    <link rel="stylesheet" href="../css/add_user.css" type="text/css" />
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
            邮件
        </legend>
        <form action="../GoHomeServlet" target="_self" method="post">
            <p>发件人：<%=session.getAttribute("sendfrom")%>
            </p>
            <p>收件人：<%=session.getAttribute("sendto")%>
            </p>
            <p>主题：<%=session.getAttribute("Theme")%>
            </p>
            <p>时间：<%=session.getAttribute("date")%>
            </p>
            <p>正文：<%=session.getAttribute("content")%>
            </p>
            <p>
                <input type="submit" class="inputbtn" value="返回">
            </p>
        </form>
    </fieldset>
</div>
<div class="a-ft"></div>
</body>
</html>