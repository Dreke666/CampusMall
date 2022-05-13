<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<title>购物车</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/public.css">
	<link rel="stylesheet" type="text/css" href="css/reclassify.css">
	<link rel="stylesheet" type="text/css" href="css/cart.css">
</head>
<body style="background: #FFF;">
	
	<jsp:include page="header.jsp"/>
	
	<div id="main" class="main">
	
		<!-- 购物车列表 -->
		<c:if test="${cartList!=null && fn:length(cartList)>00}">
			<div id="myCart">
				<div class="cart-tit">
				    <ul>
				        <li class="li2">商品封面</li>
				        <li class="li3">商品信息</li>
				        <li class="li4">规格</li>
				        <li class="li5">单价(元)</li>
				        <li class="li6">数量</li>
				        <li class="li7">小计(元)</li>
				        <li class="li8">操作</li>
				    </ul>
				</div>
				
				<c:forEach items="${cartList}" var="cart">
					<div class="cart-cont">
					    <ul class="ul1">
				            <li class="li2">
				                <a href="detail?id=${cart.good.id}">
				                    <img src="${cart.good.cover}">
				                </a>
				            </li>
				            <li class="li3">
				                <p class="goods-name">${cart.good.name}</p>
				                <p class="goods-des">${cart.good.intro}</p>
				            </li>
				            <li class="li4">
				                <p><span>${cart.good.spec}</span><br></p>
				            </li>
				            <li class="li5">￥<span class="price">${cart.good.price}</span></li>
				            <li class="li6">
				            	<span class="cut" style="cursor: pointer" onclick="cartLess(this,${cart.id})">-</span>
				                <input type="text" class="sum" value="${cart.amount}" readonly="readonly">
				                <span class="add" style="cursor: pointer" onclick="cartAdd(this,${cart.id})">+</span>
				            </li>
				            <li class="li7">￥<span class="subtotal">${cart.total}</span></li>
				            <li class="li8">
				                <i style="cursor: pointer" class="iconfont icon-shanchu" onclick="cartDelete(this,${cart.id})"></i>
				            </li>
				        </ul>
					</div>
				</c:forEach>
				
				<div class="cart-foot">
				    <div class="foot-right">
				        <div class="mdd">
				            <p class="one">
				                <span>订单总额 ：<span class="price">￥<span class="m-price">${cartTotal}</span></span></span>
				            </p>
				        </div>
				        <a href="orderSave">
				        	<span class="js" style="background: rgb(255, 113, 43);">去结算</span>
				        </a>
				    </div>
				</div>
			</div>
		</c:if>
		
		<!-- 购物车空 -->
		<c:if test="${cartList==null || fn:length(cartList)==0}">
			<div id="cartnull" style="display:block;">
				<div class="null-left">
	    			<img src="img/cart.png">
				</div>
				<div class="null-right">
				    <span>购物车还是空的呢！快去选购心仪的商品吧~~</span>
				    <a href="index"><p>去逛逛~</p></a>
				</div>
			</div>
		</c:if>
		
	</div>

	<jsp:include page="footer.jsp"/>

</body>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
// 添加
function cartAdd(obj, id){
	var num = parseInt($(obj).prev().val());
	var index = layer.load();
	$.post("cartAdd", {id:id}, function(data){
		layer.close(index);
		if(data){
			$(obj).prev().val(num + 1);
			updateSubtotal(obj); // 更新小计
			reloadTotal(); // 更新订单总额
		}else{
			layer.msg("失败了");
		}
	});
}
// 减少
function cartLess(obj, id){
	var num = parseInt($(obj).next().val());
	var index = layer.load();
	$.post("cartLess", {id:id}, function(data){
		layer.close(index);
		if(data){
			if(num == 1){
				$(obj).parents(".cart-cont").fadeOut(300);
			}else{
				$(obj).next().val(num - 1);
				updateSubtotal(obj); // 更新小计
			}
			reloadTotal(); // 更新订单总额
		}else{
			layer.msg("失败了");
		}
	});
}
// 删除
function cartDelete(obj, id){
	var num = parseInt($(obj).parents(".cart-cont").find(".sum").val());
	var index = layer.load();
	$.post("cartDelete", {id:id}, function(data){
		layer.close(index);
		if(data){
			$(obj).parents(".cart-cont").fadeOut(300);
			reloadTotal(); // 更新订单总额
		}else{
			layer.msg("失败了");
		}
	});
}
// 更新小计
function updateSubtotal(obj){
	var price = parseInt($(obj).parents(".ul1").find(".price").text());
	var sum = parseInt($(obj).parents(".ul1").find(".sum").val());
	$(obj).parents(".ul1").find(".subtotal").text(price * sum);
}
// 刷新订单总额
function reloadTotal(){
	var index = layer.load();
	$.get("cartTotal", function(data){
		layer.close(index);
		$(".m-price").text(data);
	});
}
</script>
</html>