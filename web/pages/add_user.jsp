<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>添加用户-Wemail</title>
	<link rel="stylesheet" href="css/add_user.css" type="text/css" />
	<meta name="author" content="sgafei">
</head>
<body>
	<div class="a-hd"></div>
	<div class="a-sd"></div>
	<div class="a-mn">
		<fieldset>
			<legend>
				添加用户
			</legend>
<%--			<%=session.getAttribute("username")%>;--%>
<%--			<%=session.getAttribute("nickname")%>--%>
			<form action="AddAccServlet" target="_self" method="post">
				<p>
					<input id="NNickName" name="NNickName" type="text" class="inputtxt" placeholder="请输入昵称" required="required">
				</p>
				<p>
					<input id="NAccount" name="NAccount" type="text" class="inputtxt" placeholder="请输入邮箱号" required="required">
				</p>
				<p>
					<input id="NPassWord" name="NPassWord" type="password" class="inputtxt" placeholder="请输入密码" required="required">
				</p>
				<p>
					<input id="ANPassWord" name="ANPassWord" type="password" class="inputtxt" placeholder="请再次输入密码" required="required">
				</p>
				<p><input id="sub-add-user" type="submit" class="inputbtn" value="提交"></p>
			</form>
			<form action="GoHomeServlet" target="_self" method="post">
				<p>
					<input type="submit" class="inputbtn" value="返回">
				</p>
			</form>
		</fieldset>
	</div>
	<div class="a-ft"></div>
</body>
</html>