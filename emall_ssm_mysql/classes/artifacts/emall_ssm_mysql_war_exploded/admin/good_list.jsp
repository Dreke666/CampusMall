<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>商品列表</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="css/bootstrap.css"/> 
<link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body >
<div class="container">

	<%@include file="header.jsp" %>
	
	<div class="text-right"><a class="btn btn-warning" href="goodAdd">添加商品</a></div>
	
	<br>
		
	<ul role="tablist" class="nav nav-tabs">
        <li <c:if test='${type==0}'>class="active"</c:if> role="presentation"><a href="goodList">全部商品</a></li>
        <li <c:if test='${type==1}'>class="active"</c:if> role="presentation"><a href="goodList?type=1">今日推荐</a></li>
    </ul>
    
    <c:if test="${type==1}"><br><p>首页只显示前[ 6 ]条记录</p></c:if>
	
	<br>

	<table class="table table-bordered table-hover">

	<tr>
		<th width="3%">ID</th>
		<th width="5%">图片</th>
		<th width="10%">名称</th>
		<th width="10%">介绍</th>
		<th width="5%">规格</th>
		<th width="3%">价格</th>
		<th width="5%">类目</th>
		<th width="3%">库存</th>
		<th width="3%">销量</th>
		<th width="5%">操作</th>
	</tr>
	
	<c:forEach var="good" items="${goodList}">
         <tr>
         	<td><p>${good.id}</p></td>
         	<td><p><a href="../index/detail?id=${good.id}" target="_blank"><img src="${good.cover}" width="100px"></a></p></td>
         	<td><p><a href="../index/detail?id=${good.id}" target="_blank">${good.name}</a></p></td>
         	<td><p>${good.intro}</p></td>
         	<td><p>${good.spec}</p></td>
         	<td><p>${good.price}</p></td>
         	<td><p>${good.type.name}</p></td>
         	<td><p>${good.stock}</p></td>
         	<td><p>${good.sales}</p></td>
			<td>
				<p>
					<c:if test="${good.top}"><a class="btn btn-success topDelete" href="javascript:;" type="1" goodId="${good.id}" text="加入今日推荐">移出今日推荐</a></c:if>
					<c:if test="${!good.top}"><a class="btn btn-primary topSave" href="javascript:;" type="1" goodId="${good.id}" text="移出今日推荐">加入今日推荐</a></c:if>
				</p>
				<a class="btn btn-info" href="goodEdit?id=${good.id}">修改</a>
				<a class="btn btn-danger" href="goodDelete?id=${good.id}">删除</a>
			</td>
       	</tr>
     </c:forEach>
     
</table>

<br>${pageTool}<br>
</div>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
$(function(){
	$(document).on("click", ".topSave", function(){
		var type = $(this).attr("type");
		var goodId = $(this).attr("goodId");
		var text = $(this).attr("text");
		var old = $(this).text();
		var obj = this;
		$.post("topSave.action", {"goodId": goodId, "type": type}, function(data){
			if(data=="ok"){
				$(obj).text(text).attr("class", "btn btn-success topDelete").attr("text", old);
			}else{
				alert("操作失败!");
			}
		}, "text");
	});
	$(document).on("click", ".topDelete", function(){
		var type = $(this).attr("type");
		var goodId = $(this).attr("goodId");
		var text = $(this).attr("text");
		var old = $(this).text();
		var obj = this;
		$.post("topDelete.action", {"goodId": goodId, "type": type}, function(data){
			if(data=="ok"){
				$(obj).text(text).attr("class", "btn btn-primary topSave").attr("text", old);
			}else{
				alert("操作失败!");
			}
		}, "text");
	});
});
</script>
</body>
</html>