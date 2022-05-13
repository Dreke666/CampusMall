<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>重置密码</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="css/bootstrap.css"/> 
</head>
<body>
<div class="container">

	<%@include file="header.jsp"%>

	<br><br>
		
	<c:if test="${msg != null}">
		<div class="alert alert-success" role="alert">${msg}</div>
	</c:if>
	
	<form class="form-horizontal" action="adminReset" method="post">
		<input type="hidden" name="id" value="${admin.id}">
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">用户名</label>
			<div class="col-sm-5">${admin.username}</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">密码</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="password" value="" required="required">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<button type="submit" class="btn btn-success">提交修改</button>
			</div>
		</div>
	</form>
	
</div>	
</body>
</html>