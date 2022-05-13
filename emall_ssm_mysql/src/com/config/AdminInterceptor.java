package com.config;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * ��̨������
 */
public class AdminInterceptor extends HandlerInterceptorAdapter{

	/**
	 * ����¼״̬
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if(uri.contains("img") || uri.contains("css")  || uri.contains("js") 
				|| uri.contains("login") || uri.contains("logout")) {
			return true; // ������·��
		}
		Object admin = request.getSession().getAttribute("admin");
		if (Objects.nonNull(admin) && !admin.toString().trim().isEmpty()) {
			return true; // ��¼��֤ͨ��
		}
		response.sendRedirect("login.jsp");
		return false; // �������һ������
	}

}
