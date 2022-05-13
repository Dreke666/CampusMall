<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>客户添加</title>
<meta charset="utf-8" />
<link rel="stylesheet" href="css/bootstrap.css" />
</head>
<body>
<div class="container">

	<%@include file="header.jsp"%>

	<br><br>
		
	<c:if test="${msg != null}">
		<div class="alert alert-danger" role="alert">${msg}</div>
	</c:if>
	
	<form class="form-horizontal" action="userSave" method="post">
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">用户</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="username" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">密码</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="password" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">姓名</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="name" >
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">电话</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="phone" >
			</div>
		</div>
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">地址</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="address" >
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<button type="submit" class="btn btn-success">提交保存</button>
			</div>
		</div>
	</form>
	
</div>
</body>
</html>