package com.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.*;
import com.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.service.AdminService;
import com.service.GoodService;
import com.service.OrderService;
import com.service.TopService;
import com.service.TypeService;
import com.service.UserService;
import com.util.PageUtil;
import com.util.UploadUtil;

/**
 * 后台管理接口
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodService goodService;
	@Autowired
	private TopService topService;
	@Autowired
	private TypeService typeService;

	/**
	 * 管理员登录 （逻辑判断）
	 * @return
	 */
	@GetMapping("/login")
	public String log() {
		return "/admin/login.jsp";
	}
	
	/**
	 * 管理员登录
	 * @return
	 */
	@PostMapping("/login")
	public String login(String username, String password, 
			HttpServletRequest request, HttpSession session) {
		Admins admin = adminService.getByUsernameAndPassword(username, password);
		if (Objects.nonNull(admin)) {
			session.setAttribute("admin", admin);
			return "redirect:index";
		}
		request.setAttribute("msg", "用户名或密码错误!");
		return "/admin/login.jsp";
	}

	/**
	 * 退出
	 * @return
	 */
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("admin");
		return "/admin/login.jsp";
	}
	
	/**
	 * 后台首页
	 * @return
	 */
	@GetMapping("/index")
	public String index(HttpServletRequest request) {
		request.setAttribute("msg", "恭喜你! 登录成功了");
		return "/admin/index.jsp";
	}

	/**
	 * 类目列表
	 * @return
	 */
	@GetMapping("/typeList")
	public String typeList(HttpServletRequest request) {
		request.setAttribute("flag", 1);
		request.setAttribute("typeList", typeService.getList());
		return "/admin/type_list.jsp";
	}

	/**
	 * 类目添加
	 * @return
	 */
	@GetMapping("/typeAdd")
	public String typeAdd(HttpServletRequest request) {
		request.setAttribute("flag", 1);
		return "/admin/type_add.jsp";
	}
	
	/**
	 * 类目添加
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/typeSave")
	public String typeSave(Types type) throws Exception {
		typeService.add(type);
		return "redirect:typeList?flag=1";
	}

	/**
	 * 类目更新
	 * @return
	 */
	@GetMapping("/typeEdit")
	public String typeEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 1);
		request.setAttribute("type", typeService.get(id));
		return "/admin/type_edit.jsp";
	}

	/**
	 * 类目更新
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/typeUpdate")
	public String typeUpdate(Types type) throws Exception {
		typeService.update(type);
		return "redirect:typeList?flag=1";
	}

	/**
	 * 类目删除
	 * @return
	 */
	@GetMapping("/typeDelete")
	public String typeDelete(int id) {
		typeService.delete(id);
		return "redirect:typeList?flag=1";
	}

	/**
	 * 产品列表
	 * @return
	 */
	@GetMapping("/goodList")
	public String goodList(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="0") byte type,
			@RequestParam(required=false, defaultValue="1") int page,
			@RequestParam(required=false, defaultValue="10") int size) {
		request.setAttribute("flag", 2);
		request.setAttribute("page", page);
		request.setAttribute("type", type);
		if(type > 0) {
			String goodIds = topService.getGoodIdsByType(type);
			request.setAttribute("goodList", goodService.getListByIds(goodIds, page, size));
			request.setAttribute("pageTool", PageUtil.getPageTool(request, goodService.getCountByIds(goodIds), page, size));
		}else {
			request.setAttribute("goodList", goodService.getList(page, size));
			request.setAttribute("pageTool", PageUtil.getPageTool(request, goodService.getCount(), page, size));
		}
		return "/admin/good_list.jsp";
	}

	/**
	 * 产品添加
	 * @return
	 */
	@GetMapping("/goodAdd")
	public String goodAdd(HttpServletRequest request) {
		request.setAttribute("flag", 2);
		request.setAttribute("typeList", typeService.getList());
		return "/admin/good_add.jsp";
	}

	/**
	 * 产品添加
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/goodSave")
	public String goodSave(Goods good, MultipartFile file,
			@RequestParam(required=false, defaultValue="1") int page) throws Exception {
		good.setCover(UploadUtil.upload(file));
		goodService.add(good);
		return "redirect:goodList?flag=2&page="+page;
	}

	/**
	 * 产品更新
	 * @return
	 */
	@GetMapping("/goodEdit")
	public String goodEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 2);
		request.setAttribute("typeList", typeService.getList());
		request.setAttribute("good", goodService.get(id));
		return "/admin/good_edit.jsp";
	}

	/**
	 * 产品更新
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/goodUpdate")
	public String goodUpdate(Goods good, MultipartFile file, 
			@RequestParam(required=false, defaultValue="1") int page) throws Exception {
		if (Objects.nonNull(file) && !file.isEmpty()) {
			good.setCover(UploadUtil.upload(file));
		}
		goodService.update(good);
		return "redirect:goodList?flag=2&page="+page;
	}

	/**
	 * 产品删除
	 * @return
	 */
	@GetMapping("/goodDelete")
	public String goodDelete(int id, 
			@RequestParam(required=false, defaultValue="1") int page) {
		goodService.delete(id);
		return "redirect:goodList?flag=2&page="+page;
	}
	
	/**
	 * 添加推荐
	 * @return
	 */
	@PostMapping("/topSave")
	public @ResponseBody String topSave(int goodId, byte type) {
		return topService.add(goodId, type) ? "ok" : null;
	}
	
	/**
	 * 删除推荐
	 * @return
	 */
	@PostMapping("/topDelete")
	public @ResponseBody String topDelete(int goodId, byte type) {
		return topService.delete(goodId, type) ? "ok" : null;
	}

	/**
	 * 订单列表
	 * @return
	 */
	@GetMapping("/orderList")
	public String orderList(HttpServletRequest request,
			@RequestParam(required=false, defaultValue="0")byte status, 
			@RequestParam(required=false, defaultValue="1") int page, 
			@RequestParam(required=false, defaultValue="10") int size) {
		request.setAttribute("flag", 3);
		request.setAttribute("status", status);
		request.setAttribute("orderList", orderService.getList(status, page, size));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, orderService.getCount(status), page, size));
		return "/admin/order_list.jsp";
	}

	/**
	 * 订单发货
	 * @return
	 */
	@GetMapping("/orderSend")
	public String orderSend(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.send(id);
		return "redirect:orderList?flag=3&status="+status+"&page="+page;
	}
	
	/**
	 * 订单完成
	 * @return
	 */
	@GetMapping("/orderFinish")
	public String orderFinish(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.finish(id);
		return "redirect:orderList?flag=3&status="+status+"&page="+page;
	}

	/**
	 * 订单删除
	 * @return
	 */
	@GetMapping("/orderDelete")
	public String orderDelete(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.delete(id);
		return "redirect:orderList?flag=3&status="+status+"&page="+page;
	}

	/**
	 * 顾客管理
	 * @return
	 */
	@GetMapping("/userList")
	public String userList(HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1") int page, 
			@RequestParam(required=false, defaultValue="10") int size) {
		request.setAttribute("flag", 4);
		request.setAttribute("userList", userService.getList(page, size));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, userService.getCount(), page, size));
		return "/admin/user_list.jsp";
	}

	/**
	 * 顾客添加
	 * @return
	 */
	@GetMapping("/userAdd")
	public String userAdd(HttpServletRequest request) {
		request.setAttribute("flag", 4);
		return "/admin/user_add.jsp";
	}

	/**
	 * 顾客添加
	 * @return
	 */
	@PostMapping("/userSave")
	public String userSave(Users user, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		if (Objects.nonNull(userService.getByUsername(user.getUsername()))) {
			request.setAttribute("msg", "用户名已存在!");
			return "/admin/user_add.jsp";
		}
		userService.add(user);
		return "redirect:userList?flag=4&page="+page;
	}

	/**
	 * 顾客密码重置页面
	 * @return
	 */
	@GetMapping("/userRe")
	public String userRe(int id, HttpServletRequest request) {
		request.setAttribute("flag", 4);
		request.setAttribute("user", userService.get(id));
		return "/admin/user_reset.jsp";
	}

	/**
	 * 顾客密码重置
	 * @return
	 */
	@PostMapping("/userReset")
	public String userReset(int id, String password, 
			@RequestParam(required=false, defaultValue="1") int page) {
		userService.updatePassword(id, password);
		return "redirect:userList?page="+page;
	}

	/**
	 * 顾客更新
	 * @return
	 */
	@GetMapping("/userEdit")
	public String userEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 4);
		request.setAttribute("user", userService.get(id));
		return "/admin/user_edit.jsp";
	}

	/**
	 * 顾客更新
	 * @return
	 */
	@PostMapping("/userUpdate")
	public String userUpdate(int id, String name, String phone, String address, 
			@RequestParam(required=false, defaultValue="1") int page) {
		userService.update(id, name, phone, address);
		return "redirect:userList?flag=4&page="+page;
	}

	/**
	 * 顾客删除
	 * @return
	 */
	@GetMapping("/userDelete")
	public String userDelete(int id, 
			@RequestParam(required=false, defaultValue="1") int page) {
		userService.delete(id);
		return "redirect:userList?flag=4&page="+page;
	}

	/**
	 * 管理员列表
	 * @return
	 */
	@GetMapping("/adminList")
	public String adminList(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page, 
			@RequestParam(required=false, defaultValue="10") int size) {
		request.setAttribute("flag", 5);
		request.setAttribute("adminList", adminService.getList(page, size));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, adminService.getCount(), page, size));
		return "/admin/admin_list.jsp";
	}

	/**
	 * 管理员添加
	 * @return
	 */
	@GetMapping("/adminAdd")
	public String adminAdd(HttpServletRequest request) {
		request.setAttribute("flag", 5);
		return "/admin/admin_add.jsp";
	}
	
	/**
	 * 管理员密码重置
	 * @return
	 */
	@GetMapping("/adminRe")
	public String adminRe(int id, HttpServletRequest request) {
		request.setAttribute("flag", 5);
		request.setAttribute("admin", adminService.get(id));
		return "/admin/admin_reset.jsp";
	}

	/**
	 * 管理员密码重置
	 * @return
	 */
	@PostMapping("/adminReset")
	public String adminReset(int id, String password, HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1") int page) {
		adminService.update(id, password);
		return "redirect:adminList?page="+page;
	}

	/**
	 * 管理员添加
	 * @return
	 */
	@PostMapping("/adminSave")
	public String adminSave(Admins admin, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		if (Objects.nonNull(adminService.getByUsername(admin.getUsername()))) {
			request.setAttribute("msg", "用户名已存在!");
			return "/admin/admin_add.jsp";
		}
		adminService.add(admin);
		return "redirect:adminList?flag=5&page="+page;
	}

	/**
	 * 管理员删除
	 * @return
	 */
	@GetMapping("/adminDelete")
	public String adminDelete(int id, 
			@RequestParam(required=false, defaultValue="1") int page) {
		adminService.delete(id);
		return "redirect:adminList?flag=5&page="+page;
	}
	
    /**
     * 图片上传
     * @throws Exception 
     */
    @PostMapping("upload")
    public @ResponseBody Map<String, Object> upload(MultipartFile imgFile) throws Exception {
    	Map<String, Object> map = new HashMap<>();
    	if (Objects.nonNull(imgFile) && !imgFile.isEmpty()) {
    		map.put("error", 0);
    		map.put("url", UploadUtil.upload(imgFile));
    		return map;
		}else {
			map.put("error", 1);
			map.put("message", "上传出错啦");
		}
    	return map;
    }

	/**
	 * 订单导出
	 */
	@RequestMapping("orderExportExcel")
	public void orderExportExcel(HttpServletResponse response){
		//导出所有订单
		List<Orders> orderList = orderService.getAllList();
		for(Orders orders : orderList){
			if(orders.getAddress() != null && !"".equals(orders.getAddress())){
				StringBuffer buf=new StringBuffer();
				buf.append(orders.getName()+"\n"+orders.getPhone()+"\n"+orders.getAddress());
				orders.setAddress(buf.toString());
			}
			if(orders.getStatus() == Orders.STATUS_UNPAY){
				orders.setName("未付款");
			}else if(orders.getStatus() == Orders.STATUS_PAYED){
				orders.setName("已付款");
			}else if(orders.getStatus() == Orders.PAYTYPE_OFFLINE){
				orders.setName("配送中");
			}else{
				orders.setName("已完成");
			}
			if(orders.getPaytype() == Orders.PAYTYPE_WECHAT){
				orders.setPhone("微信");
			}else if(orders.getPaytype() == Orders.PAYTYPE_ALIPAY){
				orders.setPhone("支付宝");
			}else{
				orders.setPhone("积分");
			}
			StringBuffer buf1=new StringBuffer();
			orders.setUsername(orders.getUser().getName());
			for(Items items : orders.getItemList()){
				buf1.append(items.getGood().getName() + " *" + items.getAmount() + "\n");
			}
			orders.setDingdanInfo(buf1.toString());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String export="订单编号#id,订单金额#total,商品详情#dingdanInfo,收货信息#address,订单状态#name,支付方式#phone,购买用户#username";//此处为标题，excel首行的title，按照此格式即可，格式无需改动，但是可以增加或者减少项目。
			String[] excelHeader = export.split(",");
			String fileName="订单" + sdf.format(new Date()); //className:生成的excel默认文件名和sheet页
			ExcelUtils.export(response, fileName, excelHeader, orderList);//调用封装好的导出方法，具体方法在下面
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
