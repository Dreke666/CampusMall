package com.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.config.ExceptionConfig.MyException;
import com.entity.Orders;
import com.entity.Users;
import com.service.CartService;
import com.service.GoodService;
import com.service.OrderService;
import com.service.UserService;
import com.util.PageUtil;
import com.util.SafeUtil;

/**
 * 用户相关接口
 */
@Controller
@RequestMapping("/index")
public class UserController{
	
	@Resource
	private UserService userService;
	@Resource
	private GoodService goodService;
	@Resource
	private OrderService orderService;
	@Resource
	private CartService cartService;

	
	/**
	 * 用户注册
	 * @return
	 */
	@GetMapping("/register")
	public String reg() {
		return "/index/register.jsp";
	}
	
	/**
	 * 用户注册
	 * @return
	 */
	@PostMapping("/register")
	public String register(Users user, HttpServletRequest request){
		if (user.getUsername().isEmpty()) {
			request.setAttribute("msg", "用户名不能为空!");
		}else if (Objects.nonNull(userService.getByUsername(user.getUsername()))) {
			request.setAttribute("msg", "用户名已存在!");
		}else {
			userService.add(user);
			request.setAttribute("msg", "注册成功 可以去登录了!");
			return "/index/login.jsp";
		}
		return "/index/register.jsp";
	}
	
	/**
	 * 用户登录
	 * @return
	 */
	@GetMapping("/login")
	public String log() {
		return "/index/login.jsp";
	}
	
	/**
	 * 用户登录
	 * @return
	 */
	@PostMapping("/login")
	public String login(Users user, HttpServletRequest request, HttpSession session) {
		Users loginUser = userService.getByUsernameAndPassword(user.getUsername(), user.getPassword());
		if (Objects.isNull(loginUser)) {
			request.setAttribute("msg", "用户名或密码错误");
			return "/index/login.jsp";
		}
		session.setAttribute("user", loginUser);
		// 还原购物车
		session.setAttribute("cartCount", cartService.getCount(loginUser.getId()));
		String referer = request.getHeader("referer"); // 来源页面
		System.out.println(referer); //TODO
		return "redirect:index";
	}

	/**
	 * 注销登录
	 * @return
	 */
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		session.removeAttribute("cartCount");
		return "/index/login.jsp";
	}

	/**
	 * 查看积分
	 */
	@GetMapping("/mypoint")
	public String mypoint() {
		return "/index/mypoint.jsp";
	}

	//积分充值
	@RequestMapping("/addPoint")
	public String addPoint(double point,HttpSession session,HttpServletRequest request){
		Users users = (Users)session.getAttribute("user");
		BigDecimal bigDecimal = new BigDecimal(Double.toString(point)).add(new BigDecimal(Double.toString(users.getPoint())));
		users.setPoint(bigDecimal.doubleValue());
		int count = userService.updatePoint(users);
		if(count > 0){
			session.setAttribute("user",users);
			request.setAttribute("msg","充值成功！");
		}else{
			request.setAttribute("msg","充值失败！");
		}
		return "/index/mypoint.jsp";
	}

	/**
	 * 查看购物车
	 * @return
	 */
	@GetMapping("/cart")
	public String cart(HttpServletRequest request, HttpSession session) {
		Users user = (Users) session.getAttribute("user");
		request.setAttribute("cartList", cartService.getList(user.getId()));
		request.setAttribute("cartCount", cartService.getCount(user.getId()));
		request.setAttribute("cartTotal", cartService.getTotal(user.getId()));
		return "/index/cart.jsp";
	}
	
	/**
	 * 购物车总金额
	 * @return
	 */
	@GetMapping("/cartTotal")
	public @ResponseBody int cartTotal(HttpSession session){
		Users user = (Users) session.getAttribute("user");
		return cartService.getTotal(user.getId());
	}
	
	/**
	 * 加入购物车
	 * @return
	 */
	@PostMapping("/cartBuy")
	public @ResponseBody boolean cartBuy(int goodId, HttpSession session){
		Users user = (Users) session.getAttribute("user");
		return cartService.save(goodId, user.getId());
	}
	
	/**
	 * 添加数量
	 */
	@PostMapping("/cartAdd")
	public @ResponseBody boolean cartAdd(int id){
		return cartService.add(id);
	}
	
	/**
	 * 减少数量
	 */
	@PostMapping("/cartLess")
	public @ResponseBody boolean cartLess(int id){
		return cartService.less(id);
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/cartDelete")
	public @ResponseBody boolean cartDelete(int id){
		return cartService.delete(id);
	}

	
	/**
	 * 查看订单
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@GetMapping("/order")
	public String order(HttpServletRequest request, HttpSession session,
			@RequestParam(required=false, defaultValue="1")int page, 
			@RequestParam(required=false, defaultValue="6")int size) throws UnsupportedEncodingException{
		Users user = (Users) session.getAttribute("user");
		request.setAttribute("orderList", orderService.getListByUserid(user.getId(), page, size));
		request.setAttribute("pageHtml", PageUtil.getPageHtml(request, orderService.getCountByUserid(user.getId()), page, size));
		return "/index/order.jsp";
	}
	
	/**
	 * 直接购买
	 * @return
	 * @throws MyException 
	 */
	@PostMapping("/orderAdd")
	public String orderAdd(int goodId, HttpSession session) throws MyException{
		Users user = (Users) session.getAttribute("user");
		int orderId = orderService.add(goodId, user.getId());
		return "redirect:orderPay?id="+orderId; // 跳转支付
	}
	
	/**
	 * 购物车结算
	 * @return
	 * @throws MyException 
	 */
	@GetMapping("/orderSave")
	public String orderSave(ServletRequest request, HttpSession session) throws MyException{
		Users user = (Users) session.getAttribute("user");
		int orderId = orderService.save(user.getId());
		session.removeAttribute("cartCount"); // 清理购物车session
		return "redirect:orderPay?id="+orderId; // 跳转支付
	}
	
	/**
	 * 支付页面
	 * @return
	 */
	@GetMapping("/orderPay")
	public String orderPay(int id, ServletRequest request) {
		request.setAttribute("order", orderService.get(id));
		return "/index/pay.jsp";
	}
	
	/**
	 * 支付(模拟)
	 * @return
	 */
	@PostMapping("/orderPay")
	@ResponseBody
	public int orderPay(Orders order,HttpSession session) {
		Users users = (Users) session.getAttribute("user");
		BigDecimal bigDecimal = new BigDecimal(0);
		if(order.getPaytype() == Orders.PAYTYPE_OFFLINE){//为积分支付时
			double d1 = order.getTotal();//商品总价
			if(users.getPoint().compareTo(d1) < 0){
				return -1;
			}else{
				//总积分 = 用户积分 - 抵扣积分
				bigDecimal = new BigDecimal(Double.toString(users.getPoint())).subtract(new BigDecimal(Double.toString(d1)));
			}
		}else{
			double d2 = order.getTotal()/100;//购买商品获取的积分
			//总积分 = 用户积分 + 获取的
			bigDecimal = new BigDecimal(Double.toString(users.getPoint())).add(new BigDecimal(Double.toString(d2)));
		}
		users.setPoint(bigDecimal.doubleValue());//保存积分
		int count = userService.updatePoint(users);
		if(count > 0){
			session.setAttribute("user",users);//更新用户积分
		}
		orderService.pay(order);
		return 1;
	}
	
	
	/**
	 * 收货地址
	 * @return
	 */
	@GetMapping("/address")
	public String address(){ // 使用session中的数据
		return "/index/address.jsp";
	}
	
	/**
	 * 修改信息
	 * @return
	 */
	@PostMapping("/addressUpdate")
	public String addressUpdate(String name, String phone, String address, HttpServletRequest request, HttpSession session){
		Users user = (Users) session.getAttribute("user");
		userService.update(user.getId(), name, phone, address);  // 更新数据库
		session.setAttribute("user", userService.get(user.getId())); // 更新session
		request.setAttribute("msg", "信息修改成功!");
		return "/index/address.jsp";
	}
	
	/**
	 * 收货地址
	 * @return
	 */
	@GetMapping("/password")
	public String password(){ // 使用session中的数据
		return "/index/password.jsp";
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@PostMapping("/passwordUpdate")
	public String passwordUpdate(String password, String passwordNew, HttpServletRequest request, HttpSession session){
		Users user = (Users) session.getAttribute("user");
		user = userService.get(user.getId());
		if(!user.getPassword().equals(SafeUtil.encode(password))) {
			request.setAttribute("msg", "原密码错误!");
		}else {
			userService.updatePassword(user.getId(), passwordNew);
			request.setAttribute("msg", "密码修改成功!");
		}
		return "/index/password.jsp";
	}
	
	/*
	 * 用???收?
	 */
	@RequestMapping("/userfinish")
	public String userFinish(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.finish(id);
		return "redirect:order?page="+page;
	}
}