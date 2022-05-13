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
 * �û���ؽӿ�
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
	 * �û�ע��
	 * @return
	 */
	@GetMapping("/register")
	public String reg() {
		return "/index/register.jsp";
	}
	
	/**
	 * �û�ע��
	 * @return
	 */
	@PostMapping("/register")
	public String register(Users user, HttpServletRequest request){
		if (user.getUsername().isEmpty()) {
			request.setAttribute("msg", "�û�������Ϊ��!");
		}else if (Objects.nonNull(userService.getByUsername(user.getUsername()))) {
			request.setAttribute("msg", "�û����Ѵ���!");
		}else {
			userService.add(user);
			request.setAttribute("msg", "ע��ɹ� ����ȥ��¼��!");
			return "/index/login.jsp";
		}
		return "/index/register.jsp";
	}
	
	/**
	 * �û���¼
	 * @return
	 */
	@GetMapping("/login")
	public String log() {
		return "/index/login.jsp";
	}
	
	/**
	 * �û���¼
	 * @return
	 */
	@PostMapping("/login")
	public String login(Users user, HttpServletRequest request, HttpSession session) {
		Users loginUser = userService.getByUsernameAndPassword(user.getUsername(), user.getPassword());
		if (Objects.isNull(loginUser)) {
			request.setAttribute("msg", "�û������������");
			return "/index/login.jsp";
		}
		session.setAttribute("user", loginUser);
		// ��ԭ���ﳵ
		session.setAttribute("cartCount", cartService.getCount(loginUser.getId()));
		String referer = request.getHeader("referer"); // ��Դҳ��
		System.out.println(referer); //TODO
		return "redirect:index";
	}

	/**
	 * ע����¼
	 * @return
	 */
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		session.removeAttribute("cartCount");
		return "/index/login.jsp";
	}

	/**
	 * �鿴����
	 */
	@GetMapping("/mypoint")
	public String mypoint() {
		return "/index/mypoint.jsp";
	}

	//���ֳ�ֵ
	@RequestMapping("/addPoint")
	public String addPoint(double point,HttpSession session,HttpServletRequest request){
		Users users = (Users)session.getAttribute("user");
		BigDecimal bigDecimal = new BigDecimal(Double.toString(point)).add(new BigDecimal(Double.toString(users.getPoint())));
		users.setPoint(bigDecimal.doubleValue());
		int count = userService.updatePoint(users);
		if(count > 0){
			session.setAttribute("user",users);
			request.setAttribute("msg","��ֵ�ɹ���");
		}else{
			request.setAttribute("msg","��ֵʧ�ܣ�");
		}
		return "/index/mypoint.jsp";
	}

	/**
	 * �鿴���ﳵ
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
	 * ���ﳵ�ܽ��
	 * @return
	 */
	@GetMapping("/cartTotal")
	public @ResponseBody int cartTotal(HttpSession session){
		Users user = (Users) session.getAttribute("user");
		return cartService.getTotal(user.getId());
	}
	
	/**
	 * ���빺�ﳵ
	 * @return
	 */
	@PostMapping("/cartBuy")
	public @ResponseBody boolean cartBuy(int goodId, HttpSession session){
		Users user = (Users) session.getAttribute("user");
		return cartService.save(goodId, user.getId());
	}
	
	/**
	 * �������
	 */
	@PostMapping("/cartAdd")
	public @ResponseBody boolean cartAdd(int id){
		return cartService.add(id);
	}
	
	/**
	 * ��������
	 */
	@PostMapping("/cartLess")
	public @ResponseBody boolean cartLess(int id){
		return cartService.less(id);
	}
	
	/**
	 * ɾ��
	 */
	@PostMapping("/cartDelete")
	public @ResponseBody boolean cartDelete(int id){
		return cartService.delete(id);
	}

	
	/**
	 * �鿴����
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
	 * ֱ�ӹ���
	 * @return
	 * @throws MyException 
	 */
	@PostMapping("/orderAdd")
	public String orderAdd(int goodId, HttpSession session) throws MyException{
		Users user = (Users) session.getAttribute("user");
		int orderId = orderService.add(goodId, user.getId());
		return "redirect:orderPay?id="+orderId; // ��ת֧��
	}
	
	/**
	 * ���ﳵ����
	 * @return
	 * @throws MyException 
	 */
	@GetMapping("/orderSave")
	public String orderSave(ServletRequest request, HttpSession session) throws MyException{
		Users user = (Users) session.getAttribute("user");
		int orderId = orderService.save(user.getId());
		session.removeAttribute("cartCount"); // �����ﳵsession
		return "redirect:orderPay?id="+orderId; // ��ת֧��
	}
	
	/**
	 * ֧��ҳ��
	 * @return
	 */
	@GetMapping("/orderPay")
	public String orderPay(int id, ServletRequest request) {
		request.setAttribute("order", orderService.get(id));
		return "/index/pay.jsp";
	}
	
	/**
	 * ֧��(ģ��)
	 * @return
	 */
	@PostMapping("/orderPay")
	@ResponseBody
	public int orderPay(Orders order,HttpSession session) {
		Users users = (Users) session.getAttribute("user");
		BigDecimal bigDecimal = new BigDecimal(0);
		if(order.getPaytype() == Orders.PAYTYPE_OFFLINE){//Ϊ����֧��ʱ
			double d1 = order.getTotal();//��Ʒ�ܼ�
			if(users.getPoint().compareTo(d1) < 0){
				return -1;
			}else{
				//�ܻ��� = �û����� - �ֿۻ���
				bigDecimal = new BigDecimal(Double.toString(users.getPoint())).subtract(new BigDecimal(Double.toString(d1)));
			}
		}else{
			double d2 = order.getTotal()/100;//������Ʒ��ȡ�Ļ���
			//�ܻ��� = �û����� + ��ȡ��
			bigDecimal = new BigDecimal(Double.toString(users.getPoint())).add(new BigDecimal(Double.toString(d2)));
		}
		users.setPoint(bigDecimal.doubleValue());//�������
		int count = userService.updatePoint(users);
		if(count > 0){
			session.setAttribute("user",users);//�����û�����
		}
		orderService.pay(order);
		return 1;
	}
	
	
	/**
	 * �ջ���ַ
	 * @return
	 */
	@GetMapping("/address")
	public String address(){ // ʹ��session�е�����
		return "/index/address.jsp";
	}
	
	/**
	 * �޸���Ϣ
	 * @return
	 */
	@PostMapping("/addressUpdate")
	public String addressUpdate(String name, String phone, String address, HttpServletRequest request, HttpSession session){
		Users user = (Users) session.getAttribute("user");
		userService.update(user.getId(), name, phone, address);  // �������ݿ�
		session.setAttribute("user", userService.get(user.getId())); // ����session
		request.setAttribute("msg", "��Ϣ�޸ĳɹ�!");
		return "/index/address.jsp";
	}
	
	/**
	 * �ջ���ַ
	 * @return
	 */
	@GetMapping("/password")
	public String password(){ // ʹ��session�е�����
		return "/index/password.jsp";
	}
	
	/**
	 * �޸�����
	 * @return
	 */
	@PostMapping("/passwordUpdate")
	public String passwordUpdate(String password, String passwordNew, HttpServletRequest request, HttpSession session){
		Users user = (Users) session.getAttribute("user");
		user = userService.get(user.getId());
		if(!user.getPassword().equals(SafeUtil.encode(password))) {
			request.setAttribute("msg", "ԭ�������!");
		}else {
			userService.updatePassword(user.getId(), passwordNew);
			request.setAttribute("msg", "�����޸ĳɹ�!");
		}
		return "/index/password.jsp";
	}
	
	/*
	 * ��???��?
	 */
	@RequestMapping("/userfinish")
	public String userFinish(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.finish(id);
		return "redirect:order?page="+page;
	}
}