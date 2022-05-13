package com.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Goods;
import com.entity.Tops;
import com.entity.Types;
import com.service.GoodService;
import com.service.TypeService;
import com.util.PageUtil;

/**
 * 前台相关接口
 */
@Controller
@RequestMapping("/index")
public class IndexController{
	
	@Autowired
	private GoodService goodService;
	@Autowired
	private TypeService typeService;
	

	/**
	 * 首页
	 * @return
	 */
	@GetMapping("/index")
	public String index(HttpServletRequest request){
		request.setAttribute("flag", 1);
		// 今日推荐
		List<Goods> todayList = goodService.getListByTopType(Tops.TYPE_TODAY, 1, 6); // 取前6个
		request.setAttribute("todayList", todayList);
		// 热销排行
		List<Goods> hotList = goodService.getListOrderSales(1, 10); // 取前10个
		request.setAttribute("hotList", hotList);
		// 类目列表
		List<Types> typeList = typeService.getList();
		List<Map<String, Object>> dataList = new ArrayList<>();
		for(Types type : typeList) {
			Map<String, Object> map = new HashMap<>();
			map.put("type", type);
			map.put("goodList", goodService.getListByType(type.getId(), 1, 15)); // 取前15个
			dataList.add(map);
		}
		request.setAttribute("dataList", dataList);
		return "/index/index.jsp";
	}
	
	/**
	 * 商品分类
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@GetMapping("/type")
	public String type(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="0")int id, 
			@RequestParam(required=false, defaultValue="1")int page, 
			@RequestParam(required=false, defaultValue="10")int size) throws UnsupportedEncodingException {
		request.setAttribute("type", typeService.get(id));
		request.setAttribute("goodList", goodService.getListByType(id, page, size));
		request.setAttribute("pageHtml", PageUtil.getPageHtml(request, goodService.getCountByType(id), page, size));
		return "/index/goods.jsp";
	}
	
	/**
	 * 今日推荐
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@GetMapping("/today")
	public String today(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1")int page, 
			@RequestParam(required=false, defaultValue="10")int size) throws UnsupportedEncodingException {
		request.setAttribute("flag", 2);
		request.setAttribute("goodList", goodService.getListByTopType(Tops.TYPE_TODAY, page, size));
		request.setAttribute("pageHtml", PageUtil.getPageHtml(request, goodService.getCountByTopType(Tops.TYPE_TODAY), page, size));
		return "/index/goods.jsp";
	}
	
	/**
	 * 热销推荐
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@GetMapping("/hot")
	public String hot(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1")int page, 
			@RequestParam(required=false, defaultValue="10")int size) throws UnsupportedEncodingException{
		request.setAttribute("flag", 3);
		request.setAttribute("goodList", goodService.getListOrderSales(page, size));
		request.setAttribute("pageHtml", PageUtil.getPageHtml(request, goodService.getCount(), page, size));
		return "/index/goods.jsp";
	}
	
	/**
	 * 新品上市
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@GetMapping("/new")
	public String news(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1")int page, 
			@RequestParam(required=false, defaultValue="10")int size) throws UnsupportedEncodingException{
		request.setAttribute("flag", 4);
		request.setAttribute("goodList", goodService.getList(page, size));
		request.setAttribute("pageHtml", PageUtil.getPageHtml(request, goodService.getCount(), page, size));
		return "/index/goods.jsp";
	}
	
	/**
	 * 搜索
	 * @return
	 */
	@GetMapping("/search")
	public String search(String search, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1")int page, 
			@RequestParam(required=false, defaultValue="10")int size) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		search = new String(search.getBytes("utf-8"),"utf-8");
		request.setAttribute("search", search);
		System.out.println(search);
		if (Objects.nonNull(search) && !search.trim().isEmpty()) {
			request.setAttribute("goodList", goodService.getListByName(search, page, size));
			request.setAttribute("pageHtml", PageUtil.getPageHtml(request, goodService.getTotalByName(search), page, size));
		}
		return "/index/goods.jsp";
	}
	
	/**
	 * 商品详情
	 * @return
	 */
	@GetMapping("/detail")
	public String detail(int id, HttpServletRequest request){
		request.setAttribute("good", goodService.get(id));
		// 今日推荐前两个 在详情页显示
		request.setAttribute("todayList", goodService.getListByTopType(Tops.TYPE_TODAY, 1, 2));
		return "/index/detail.jsp";
	}

}