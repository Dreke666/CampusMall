<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>管理员列表</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="css/bootstrap.css"/> 
<link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body >
<div class="container">

	<%@include file="header.jsp"%>
	
	<div class="text-right"><a class="btn btn-warning" href="adminAdd">添加管理员</a></div>
		
	<br>
	
	<table class="table table-bordered table-hover">

	<tr>
		<th width="5%">ID</th>
		<th width="10%">用户名</th>
		<th width="10%">操作</th>
	</tr>
	
	<c:forEach var="admin" items="${adminList}">
         <tr>
         	<td><p>${admin.id}</p></td>
         	<td><p>${admin.username}</p></td>
			<td>
				<c:if test="${admin.id==1}"><p>系统保护用户</p></c:if>
				<c:if test="${admin.id>1}">
					<a class="btn btn-info" href="adminRe?id=${admin.id}">重置密码</a>
					<a class="btn btn-danger" href="adminDelete?id=${admin.id}">删除</a>
				</c:if>
			</td>
       	</tr>
     </c:forEach>
     
</table>

<br>${pageTool}<br>
</div>
</body>
</html>