package com.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页工具类
 */
public class PageUtil {
	
	/**
	 * 获取分页代码
	 * @param total 总记录数
	 * @param page 当前页面
	 * @param size 每页数量
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getPageHtml(HttpServletRequest request, long total, int page, int size) throws UnsupportedEncodingException{
		if (total <= 0) {
			return null;
		}
		// 计算总页数
		int pages = (int) (total % size ==0 ? total/size : total /size + 1);
		pages = pages == 0 ? 1 : pages;
		// 请求地址
		String url = request.getRequestURL().toString();
		System.out.println(url);
		// 请求参数
		StringBuilder paramBuilder = new StringBuilder();
		
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			if(param.indexOf("page") > -1) {
				continue;
			}
			
			paramBuilder.append("&").append(param).append("=").append(new String(request.getParameter(param).getBytes("ISO8859-1"),"utf-8"));
		}
		System.out.println(paramBuilder);
		// 分页字符串
		StringBuilder pageBuilder = new StringBuilder();
		pageBuilder.append("<div class='holder'>");
		// 上一页
		if (page <= 1) { // 如果已经是第一页, 上一页按钮disabled
//			pageBuilder.append("<a title='已是首页'>首页</a>");
//			pageBuilder.append("<a title='已是首页'>上页</a>");
		}else{
			pageBuilder.append("<a href='").append(url).append("?").append("page=1")
			.append(paramBuilder).append("'>首页</a>");
			pageBuilder.append("<a href='").append(url).append("?").append("page=").append(page-1)
			.append(paramBuilder).append("'>上页</a>");
		}
		// 中间数字页码
		if (pages <= 7) { // 全部显示
			for (int i = 1; i <= pages; i++) {
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, i));
			}
		}else{ // 显示部分
			if (page<4 || page>pages-3) { // 1 2 3 ... pages-2 pages-1 pages
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 1));
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 2));
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 3));
				pageBuilder.append(" ... ");
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages-2));
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages-1));
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages));
			}else{	// 1 2 ... page-1 page page+1 ... pages-1 pages
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 1));
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 2));
				pageBuilder.append(" ... ");
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, page-1));
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, page));
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, page+1));
				pageBuilder.append(" ... ");
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages-1));
				pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages));
			}
		}
		// 下一页
		if (page >= pages) { // 如果已经是最后一页, 上一页按钮disabled
//			pageBuilder.append("<a title='已是尾页'>下页</a>");
//			pageBuilder.append("<a title='已是尾页'>尾页</a>");
		}else{
			pageBuilder.append("<a href='").append(url).append("?").append("page=").append(page+1)
			.append(paramBuilder).append("'>下页</a>");
			pageBuilder.append("<a href='").append(url).append("?").append("page=").append(pages)
			.append(paramBuilder).append("'>尾页</a>");
		}
		pageBuilder.append("</div>");
		return pageBuilder.toString();
	}

	/**
	 * 封装分页项
	 * @param url
	 * @param params
	 * @param page
	 * @param i
	 * @return
	 */
	private static String packPageItem(String url, String params, int page, int i) {
		StringBuilder pageBuilder = new StringBuilder();
		if (i == page) {
			pageBuilder.append("<a class='jp-current'>").append(i).append("</a>");
		}else{
			pageBuilder.append("<a title='第").append(i).append("页' href='").append(url).append("?").append("page=").append(i)
				.append(params).append("'>");pageBuilder.append(i).append("</a>");
		}
		return pageBuilder.toString();
	}
	
	/**
	 * 获取分页代码
	 * @param total 总记录数
	 * @param page 当前页面
	 * @param size 每页数量
	 * @return
	 */
	public static String getPageTool(HttpServletRequest request, long total, int page, int size){
		long pages = total % size ==0 ? total/size : total /size + 1;
		pages = pages==0 ? 1 : pages;
		String url = request.getRequestURL().toString();
		StringBuilder queryString = new StringBuilder();
		Enumeration<String> enumeration = request.getParameterNames();
		try { // 拼装请求参数
			while (enumeration.hasMoreElements()) {
				String element = (String) enumeration.nextElement();
				if(!element.contains("page")) { // 跳过page参数
					queryString.append("&").append(element).append("=").append(java.net.URLEncoder.encode(request.getParameter(element),"UTF-8"));
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 拼装分页代码
		StringBuilder buf = new StringBuilder();
		buf.append("<div style='text-align:center;'>\n");
		if (page <= 1) {
			buf.append("<a class='btn btn-info' disabled >首页</a>\n");
		}else{
			buf.append("<a class='btn btn-info' href='").append(url).append("?page=").append(1).append(queryString).append("'>首页</a>\n");
		}
		if (page <= 1) {
			buf.append("<a class='btn btn-info' disabled >上一页</a>\n");
		}else {
			buf.append("<a class='btn btn-info' href='").append(url).append("?page=").append(page>1 ? page-1 : 1).append(queryString).append("'>上一页</a>\n");
		}
		buf.append("<h2 style='display:inline;'>[").append(page).append("/").append(pages).append("]</h2>\n");
		buf.append("<h2 style='display:inline;'>[").append(total).append("]</h2>\n");
		if (page >= pages) {
			buf.append("<a class='btn btn-info' disabled >下一页</a>\n");
		}else {
			buf.append("<a class='btn btn-info' href='").append(url).append("?page=").append(page<pages ? page+1 : pages).append(queryString).append("'>下一页</a>\n");
		}
		if (page >= pages) {
			buf.append("<a class='btn btn-info' disabled >尾页</a>\n");
		}else {
			buf.append("<a class='btn btn-info' href='").append(url).append("?page=").append(pages).append(queryString).append("'>尾页</a>\n");
		}
		buf.append("<input type='text' class='form-control' style='display:inline;width:60px;' value=''/>");
		buf.append("<a class='btn btn-info' href='javascript:void(0);' onclick='location.href=\"").append(url).append("?page=").append("\"+(this.previousSibling.value)+\"").append(queryString).append("\"'>GO</a>\n");
		buf.append("</div>\n");
		return buf.toString();
	}

}
