<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>个人中心</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/public.css">
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<link rel="stylesheet" type="text/css" href="css/address.css">
</head>
<body>
	
	<jsp:include page="header.jsp"/>
	
	<div id="main">
	
		<div class="new-address">
			<form method="post" action="myUpdate" class="registerform">
				<div class="box clearfix">
					<div class="left">
						<span>姓名&nbsp;：&nbsp;</span>
					</div>
					<div class="right">
						<input type="text" name="name" value="${sessionScope.user.name}" required="required" placeholder="请输入收货人姓名">
					<span class="Validform_checktip"></span></div>
				</div>
				<div class="box clearfix">
					<div class="left">
						<span>电话&nbsp;：&nbsp;</span>
					</div>
					<div class="right">
						<input type="text" name="phone" value="${sessionScope.user.phone}" required="required" placeholder="请输入收货人电话">
					<span class="Validform_checktip"></span></div>
				</div>
				<div class="box clearfix">
					<div class="left">
						<span>地址&nbsp;：&nbsp;</span>
					</div>
					<div class="right">
						  <input type="text" name="address" value="${sessionScope.user.address}" required="required" placeholder="请输入收货地址">
					</div>
				</div>
				<div class="save">
					<button>保存</button>
					<p style="color:green;">${msg}</p>
				</div>
			</form>
		</div>
		
	</div>

	<jsp:include page="footer.jsp"/>

</body>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">

</script>
</html>