<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>收货地址</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/public.css">
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<link rel="stylesheet" type="text/css" href="css/address.css">
</head>
<body>
	
	<jsp:include page="header.jsp"/>
	
	<div id="main">
		<div style="width: 1200px;padding: 40px 0px 10px;margin: 0 auto;">
			<div class="right" style="margin: 30px 0;">
				<span>我的积分&nbsp;：&nbsp;</span><span style="color: red;font-weight: bold;font-size: 30px;">${sessionScope.user.point }</span>
			</div>
		</div>
		<div class="new-address saoma" style="display: none;">
			<div><img src="img/pay.png" style="width: 200px;"></div>
			<div class="save">
				<button onclick="isOk()">扫码成功</button>
			</div>
		</div>
		<div class="new-address chongzhi" >
			<form method="post" action="addPoint" class="registerform" onsubmit="return false;">
				<div class="box clearfix">
					<div class="left">
						<span>充值积分&nbsp;：&nbsp;</span>
					</div>
					<div class="right">
						<input type="text" name="point" value="" required="required" placeholder="请输入需要充值的积分">
					<span class="Validform_checktip"></span></div>
				</div>
				<div class="save">
					<button onclick="saomaShow()">充值</button>
					<p style="color:green;">${msg}</p>
				</div>
			</form>
		</div>
		
	</div>

	<jsp:include page="footer.jsp"/>

</body>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	function saomaShow() {
		$(".chongzhi").hide(500);
		$(".saoma").show(500);
	}
	function isOk() {
		$(".registerform").removeAttr("onsubmit");
		$(".registerform").submit();
	}
</script>
</html>