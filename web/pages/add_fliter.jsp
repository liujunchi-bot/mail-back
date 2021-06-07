<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>添加过滤-Wemail</title>
	<link rel="stylesheet" href="css/add_user.css" type="text/css" />
	<meta name="author" content="sgafei">

</head>
<body>
	<div class="a-hd"></div>
	<div class="a-sd"></div>
	<div class="a-mn">
		<fieldset>
			<legend>
				添加过滤
			</legend>
			<form action="AddBlacklistServlet" target="_self" method="post">
				<p>
					<input type="text" name="hostAccount" class="inputtxt" placeholder="请输入账号" required="required">
				</p>
				<p>
					<input type="text" name="enemyAccount" class="inputtxt" placeholder="请输入要过滤的邮箱号">
				</p>
				<p>
					<input type="email" name="enemyIp" class="inputtxt" placeholder="请输入要过滤的域名">
				</p>
				<p><input type="submit" class="inputbtn" value="提交"></p> 		
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