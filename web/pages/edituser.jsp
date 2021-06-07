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
			<form action="../ChangeUserInfoServlet" target="_self" method="post">
				<p>
					<input type="text" name="nickname" id="nickname" value="${nickname}" class="inputtxt" placeholder="请输入新昵称" required="required">
				</p>
				<p>
					<input type="text" name="username" id="username" value="${username}" class="inputtxt" hidden>
				</p>
				<p>
					<input type="password" value="" name="e_password" id="e_password" class="inputtxt" placeholder="请输入新密码" required="required">
				</p>
				<p>
					<input type="password" value="" name="e_again_password" id="e_again_password" class="inputtxt" placeholder="请再次输入新密码" required="required">
				</p>
				<p>
					<input type="submit" class="inputbtn" value="提交">
				</p>
			</form>
			<form action="../GoHomeServlet" target="_self" method="post">
				<p>
					<input type="text" name="username" value="<%=session.getAttribute("username")%>" hidden>
					<input type="text" name="nickname" value="<%=session.getAttribute("nickname")%>" hidden>
					<input type="submit" class="inputbtn" value="返回">
				</p>
			</form>
		</fieldset>
	</div>
	<div class="a-ft"></div>
</body>
</html>