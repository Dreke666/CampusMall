package com.config;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.entity.Users;
import com.service.CartService;
import com.service.TypeService;

/**
 * 前台拦截器
 */
public class IndexInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private TypeService typeService;
	@Autowired
	private CartService cartService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute("typeList", typeService.getList()); // 为所有页面设置类目列表
		// 拦截指定路径
		String uri = request.getRequestURI();
		if(uri.contains("index/cart") || uri.contains("index/order") || uri.contains("index/my")) {
			Object user = request.getSession().getAttribute("user");
			if (Objects.isNull(user)) {
				response.sendRedirect("login");
				return false;
			}
		} // 默认放过
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 拦截指定路径
		String uri = request.getRequestURI();
		if(uri.contains("index/cart")) { // 购物车相关请求后 更新session
			Users user = (Users) request.getSession().getAttribute("user");
			request.getSession().setAttribute("cartCount", cartService.getCount(user.getId()));
		}
		super.postHandle(request, response, handler, modelAndView);
	}

}
