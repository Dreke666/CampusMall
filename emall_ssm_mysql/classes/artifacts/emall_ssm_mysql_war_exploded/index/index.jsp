<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>首页</title>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/public.css">
	<link rel="stylesheet" type="text/css" href="css/index.css">
	<link rel="stylesheet" type="text/css" href="css/swiper.css">
</head>
<body>
	
	<jsp:include page="header.jsp"/>
	
	<div class="main">
		
		<!--今日推荐-->
		<div class="m_content m_today_box">
			<div id="m_today">
				<div class="m-hot-tit clearfix">
					<div class="m-hot-tit-left"><span>今日推荐</span></div>
					<a href="today"><div class="m-hot-tit-right"><span>更多推荐 ></span></div></a>
				</div>
				<div class="mdd_today_box clearfix">
					<div class="m_today_center clearfix">
					
						<c:forEach items="${todayList}" var="today">
							<div class="goods-box2 ">
								<div class="goods clearfix">
									<a href="detail?id=${today.id}">
										<div class="img-box">
											<img src="${today.cover}">
										</div>
									</a>
									<div class="goods-right">
										<a href="detail?id=${today.id}">
											<p class="goodsname">${today.name}</p>
										</a>
										<p class="goods-descript">${today.intro}</p>
										<p class="goods-spec">${today.spec}</p>
										<p class="goodsprices"><span class="yj">&yen;<span class="yjcont">${today.price}</span></span></p>
										<p class="addcart" data-id="${today.id}"><i class="iconfont icon-gouwuche3"></i>加入购物车</p>
									</div>
								</div>
							</div>
						</c:forEach>
						
					</div>
				</div>
			</div>
		</div>
		
	
		<!--热销排行-->
		<div class="m_content mdd-top">
			<div id="m_hot">
				<div class="m-hot-tit clearfix">
					<div class="m-hot-tit-left"><span>热销排行</span></div>
					<a href="hot"><div class="m-hot-tit-right"><span>更多热销 ></span></div></a>
				</div>
				<div class="m_hot_one clearfix">
				
					<c:forEach items="${hotList}" var="hot" begin="0" end="2" varStatus="status">
						<div class="goods-box1 ">
							<div class="goods clearfix">
								<div class="m-pm" style="background-image: url(img/m_top_${status.count}.png)"></div>
								<a href="detail?id=${hot.id}">
									<div class="img-box">
										<img src="${hot.cover}">
									</div>
								</a>
								<div class="goods-right">
									<a href="detail?id=${hot.id}">
										<p class="goodsname">${hot.name}</p>
									</a>
									<p class="goods-descript">${hot.intro}</p>
									<p class="goods-spec">${hot.spec}</p>
									<p class="goodsprices"><span class="yj">&yen;<span class="yjcont">${hot.price}</span></span></p>
									<p class="addcart" data-id="${hot.id}"><i class="iconfont icon-gouwuche3"></i>加入购物车</p>
								</div>
							</div>
						</div>
					</c:forEach>
					
				</div>
					
				<div class="m_hot_two clearfix">
				
					<c:forEach items="${hotList}" var="hot" begin="4" end="8" varStatus="status">
						<div class="goods-box ">
							<div class="goods clearfix">
								<div class="m-pm"><span>${status.count + 3}</span></div>
								<a href="detail?id=${hot.id}">
									<div class="img-box">
										<img src="${hot.cover}">
									</div>
								</a>
								<div class="goods-right">
									<a href="detail?id=${hot.id}">
										<p class="goodsname">${hot.name}</p>
									</a>
									<p class="goods-descript">${hot.intro}</p>
									<p class="goods-spec">${hot.spec}</p>
									<p class="goodsprices"><span class="yj">&yen;<span class="yjcont">${hot.price}</span></span></p>
									<p class="addcart" data-id="${hot.id}"><i class="iconfont icon-gouwuche3"></i>加入购物车</p>
								</div>
							</div>
						</div>
					</c:forEach>	
						
				</div>
			</div>
		</div>	
		
		
		<!--产品分类-->
		<c:forEach items="${dataList}" var="data">
			<div class="main Louti vegetable">
				<div class="m-hot-tit clearfix">
					<div class="m-hot-tit-left"><span>${data.type.name}</span></div>
					<a href="type?id=${data.type.id}"><div class="m-hot-tit-right">查看更多 ></div></a>
				</div>
				<div class="main-box">
					<div class="m-swiper-container">
						<div class="swiper-wrapper">
								
							<div class="swiper-slide">
								<c:forEach items="${data.goodList}" var="good" varStatus="status">
									<div class="goods-box ">
										<div class="goods">
											<a href="detail?id=${good.id}">
												<div class="img-box">
													<img src="${good.cover}">
												</div>
											</a>
											<a href="detail?id=${good.id}">
												<p class="goodsname">${good.name}</p>
											</a>
											<p class="goods-descript">${good.intro}</p>
											<p class="goods-spec">${good.spec}</p>
											<p class="goodsprices"><span class="yj">&yen;<span class="yjcont">${good.price}</span></del></p>
											<p class="addcart" data-id="${good.id}"><i class="iconfont icon-gouwuche3"></i>加入购物车</p>
										</div>
									</div>
									<c:if test="${status.count%5==0}">
										</div><div class="swiper-slide">
									</c:if>
								</c:forEach>
							</div>
	
						</div>
						<div class="swiper-pagination"></div>
					</div>
				</div>
			</div>	
		</c:forEach>

	</div>

	<jsp:include page="footer.jsp"/>

</body>

<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/jquery.fly.min.js" type="text/javascript"></script>
<script src="js/jquery.flexslider.min.js" type="text/javascript"></script>
<script src="js/swiper.min.js" type="text/javascript"></script>
<script type="text/javascript">
// 商品轮播
new Swiper('.m-swiper-container', {
	pagination: {
		el: '.swiper-pagination',
		clickable: true,
	},
});

// 显示购物车按钮
$(".goods-box").on("mouseenter", function() {
	$(this).addClass("active")
	$(this).find(".addcart").show()
	$(this).find(".reserve").show()
	$(this).find(".m_stop").show()
	$(this).find(".goodsprices").hide()
});
$(".goods-box").on("mouseleave", function() {
	$(this).removeClass("active")
	$(this).find(".addcart").hide()
	$(this).find(".reserve").hide()
	$(this).find(".m_stop").hide()
	$(this).find(".goodsprices").show()
});
$(".goods-box1").on("mouseenter", function() {
	$(this).addClass("active")
});
$(".goods-box1").on("mouseleave", function() {
	$(this).removeClass("active")
});
$(".goods-box2").on("mouseenter", function() {
	$(this).addClass("active")
	$(this).find(".addcart").show()
	$(this).find(".reserve").show()
	$(this).find(".goodsprices").hide()
});
$(".goods-box2").on("mouseleave", function() {
	$(this).removeClass("active")
	$(this).find(".addcart").hide()
	$(this).find(".reserve").hide()
	$(this).find(".goodsprices").show()
});

</script>

</html>