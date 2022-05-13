<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>注册</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/public.css">
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<link rel="stylesheet" type="text/css" href="css/logon.css">
	<link rel="stylesheet" type="text/css" href="css/font.css">
</head>
<body>
	
	<jsp:include page="header.jsp"/>
	
	<div id="main" class="logincoent">
	
		<div class="rebackgroundimg">
			<div class="registruer" style="height: 600px;">
				<div class="regrnamepwd">
					<p class="iteljk">注册<span><a href="login">已注册可直接登录</a></span></p>
					<form action="register" method="post">
					   <ul class="ul_form">
							<li class="user">
								<i class="iconfont icon-yonghu"></i>
								<input type="text" class="username" name="username" required="required" placeholder="请输入用户名">
							</li>
							<li class="pwdmia">
								<i class="iconfont icon-mima"></i>
								<input type="password" class="pwd" name="password" required="required" placeholder="请输入密码">
							</li>
							<li class="pwdmia">
								<i class="iconfont icon-zhanghuxinxi"></i>
								<input type="text" class="pwd" name="name" placeholder="请输入姓名">
							</li>
							<li class="pwdmia">
								<i class="iconfont icon-shouji"></i>
								<input type="text" class="pwd" name="phone" placeholder="电话">
							</li>
							<li class="pwdmia">
								<i class="iconfont icon-home_list"></i>
								<input type="text" class="pwd" name="address" placeholder="收货地址">
							</li>
						</ul>
						<input type="submit" value="立即注册" class="sub">
						<div style="color: red;font-size: 16px;text-align: center;margin-top: 20px;">${msg}</div>
					</form>
				</div>
			</div>
		</div>
		
	</div>

	<jsp:include page="footer.jsp"/>

</body>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">

</script>
</html>