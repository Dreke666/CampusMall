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
 * ��̨����ӿ�
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
	 * ����Ա��¼ ���߼��жϣ�
	 * @return
	 */
	@GetMapping("/login")
	public String log() {
		return "/admin/login.jsp";
	}
	
	/**
	 * ����Ա��¼
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
		request.setAttribute("msg", "�û������������!");
		return "/admin/login.jsp";
	}

	/**
	 * �˳�
	 * @return
	 */
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("admin");
		return "/admin/login.jsp";
	}
	
	/**
	 * ��̨��ҳ
	 * @return
	 */
	@GetMapping("/index")
	public String index(HttpServletRequest request) {
		request.setAttribute("msg", "��ϲ��! ��¼�ɹ���");
		return "/admin/index.jsp";
	}

	/**
	 * ��Ŀ�б�
	 * @return
	 */
	@GetMapping("/typeList")
	public String typeList(HttpServletRequest request) {
		request.setAttribute("flag", 1);
		request.setAttribute("typeList", typeService.getList());
		return "/admin/type_list.jsp";
	}

	/**
	 * ��Ŀ���
	 * @return
	 */
	@GetMapping("/typeAdd")
	public String typeAdd(HttpServletRequest request) {
		request.setAttribute("flag", 1);
		return "/admin/type_add.jsp";
	}
	
	/**
	 * ��Ŀ���
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/typeSave")
	public String typeSave(Types type) throws Exception {
		typeService.add(type);
		return "redirect:typeList?flag=1";
	}

	/**
	 * ��Ŀ����
	 * @return
	 */
	@GetMapping("/typeEdit")
	public String typeEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 1);
		request.setAttribute("type", typeService.get(id));
		return "/admin/type_edit.jsp";
	}

	/**
	 * ��Ŀ����
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/typeUpdate")
	public String typeUpdate(Types type) throws Exception {
		typeService.update(type);
		return "redirect:typeList?flag=1";
	}

	/**
	 * ��Ŀɾ��
	 * @return
	 */
	@GetMapping("/typeDelete")
	public String typeDelete(int id) {
		typeService.delete(id);
		return "redirect:typeList?flag=1";
	}

	/**
	 * ��Ʒ�б�
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
	 * ��Ʒ���
	 * @return
	 */
	@GetMapping("/goodAdd")
	public String goodAdd(HttpServletRequest request) {
		request.setAttribute("flag", 2);
		request.setAttribute("typeList", typeService.getList());
		return "/admin/good_add.jsp";
	}

	/**
	 * ��Ʒ���
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
	 * ��Ʒ����
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
	 * ��Ʒ����
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
	 * ��Ʒɾ��
	 * @return
	 */
	@GetMapping("/goodDelete")
	public String goodDelete(int id, 
			@RequestParam(required=false, defaultValue="1") int page) {
		goodService.delete(id);
		return "redirect:goodList?flag=2&page="+page;
	}
	
	/**
	 * ����Ƽ�
	 * @return
	 */
	@PostMapping("/topSave")
	public @ResponseBody String topSave(int goodId, byte type) {
		return topService.add(goodId, type) ? "ok" : null;
	}
	
	/**
	 * ɾ���Ƽ�
	 * @return
	 */
	@PostMapping("/topDelete")
	public @ResponseBody String topDelete(int goodId, byte type) {
		return topService.delete(goodId, type) ? "ok" : null;
	}

	/**
	 * �����б�
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
	 * ��������
	 * @return
	 */
	@GetMapping("/orderSend")
	public String orderSend(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.send(id);
		return "redirect:orderList?flag=3&status="+status+"&page="+page;
	}
	
	/**
	 * �������
	 * @return
	 */
	@GetMapping("/orderFinish")
	public String orderFinish(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.finish(id);
		return "redirect:orderList?flag=3&status="+status+"&page="+page;
	}

	/**
	 * ����ɾ��
	 * @return
	 */
	@GetMapping("/orderDelete")
	public String orderDelete(int id, byte status,
			@RequestParam(required=false, defaultValue="1") int page) {
		orderService.delete(id);
		return "redirect:orderList?flag=3&status="+status+"&page="+page;
	}

	/**
	 * �˿͹���
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
	 * �˿����
	 * @return
	 */
	@GetMapping("/userAdd")
	public String userAdd(HttpServletRequest request) {
		request.setAttribute("flag", 4);
		return "/admin/user_add.jsp";
	}

	/**
	 * �˿����
	 * @return
	 */
	@PostMapping("/userSave")
	public String userSave(Users user, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		if (Objects.nonNull(userService.getByUsername(user.getUsername()))) {
			request.setAttribute("msg", "�û����Ѵ���!");
			return "/admin/user_add.jsp";
		}
		userService.add(user);
		return "redirect:userList?flag=4&page="+page;
	}

	/**
	 * �˿���������ҳ��
	 * @return
	 */
	@GetMapping("/userRe")
	public String userRe(int id, HttpServletRequest request) {
		request.setAttribute("flag", 4);
		request.setAttribute("user", userService.get(id));
		return "/admin/user_reset.jsp";
	}

	/**
	 * �˿���������
	 * @return
	 */
	@PostMapping("/userReset")
	public String userReset(int id, String password, 
			@RequestParam(required=false, defaultValue="1") int page) {
		userService.updatePassword(id, password);
		return "redirect:userList?page="+page;
	}

	/**
	 * �˿͸���
	 * @return
	 */
	@GetMapping("/userEdit")
	public String userEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 4);
		request.setAttribute("user", userService.get(id));
		return "/admin/user_edit.jsp";
	}

	/**
	 * �˿͸���
	 * @return
	 */
	@PostMapping("/userUpdate")
	public String userUpdate(int id, String name, String phone, String address, 
			@RequestParam(required=false, defaultValue="1") int page) {
		userService.update(id, name, phone, address);
		return "redirect:userList?flag=4&page="+page;
	}

	/**
	 * �˿�ɾ��
	 * @return
	 */
	@GetMapping("/userDelete")
	public String userDelete(int id, 
			@RequestParam(required=false, defaultValue="1") int page) {
		userService.delete(id);
		return "redirect:userList?flag=4&page="+page;
	}

	/**
	 * ����Ա�б�
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
	 * ����Ա���
	 * @return
	 */
	@GetMapping("/adminAdd")
	public String adminAdd(HttpServletRequest request) {
		request.setAttribute("flag", 5);
		return "/admin/admin_add.jsp";
	}
	
	/**
	 * ����Ա��������
	 * @return
	 */
	@GetMapping("/adminRe")
	public String adminRe(int id, HttpServletRequest request) {
		request.setAttribute("flag", 5);
		request.setAttribute("admin", adminService.get(id));
		return "/admin/admin_reset.jsp";
	}

	/**
	 * ����Ա��������
	 * @return
	 */
	@PostMapping("/adminReset")
	public String adminReset(int id, String password, HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1") int page) {
		adminService.update(id, password);
		return "redirect:adminList?page="+page;
	}

	/**
	 * ����Ա���
	 * @return
	 */
	@PostMapping("/adminSave")
	public String adminSave(Admins admin, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") int page) {
		if (Objects.nonNull(adminService.getByUsername(admin.getUsername()))) {
			request.setAttribute("msg", "�û����Ѵ���!");
			return "/admin/admin_add.jsp";
		}
		adminService.add(admin);
		return "redirect:adminList?flag=5&page="+page;
	}

	/**
	 * ����Աɾ��
	 * @return
	 */
	@GetMapping("/adminDelete")
	public String adminDelete(int id, 
			@RequestParam(required=false, defaultValue="1") int page) {
		adminService.delete(id);
		return "redirect:adminList?flag=5&page="+page;
	}
	
    /**
     * ͼƬ�ϴ�
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
			map.put("message", "�ϴ�������");
		}
    	return map;
    }

	/**
	 * ��������
	 */
	@RequestMapping("orderExportExcel")
	public void orderExportExcel(HttpServletResponse response){
		//�������ж���
		List<Orders> orderList = orderService.getAllList();
		for(Orders orders : orderList){
			if(orders.getAddress() != null && !"".equals(orders.getAddress())){
				StringBuffer buf=new StringBuffer();
				buf.append(orders.getName()+"\n"+orders.getPhone()+"\n"+orders.getAddress());
				orders.setAddress(buf.toString());
			}
			if(orders.getStatus() == Orders.STATUS_UNPAY){
				orders.setName("δ����");
			}else if(orders.getStatus() == Orders.STATUS_PAYED){
				orders.setName("�Ѹ���");
			}else if(orders.getStatus() == Orders.PAYTYPE_OFFLINE){
				orders.setName("������");
			}else{
				orders.setName("�����");
			}
			if(orders.getPaytype() == Orders.PAYTYPE_WECHAT){
				orders.setPhone("΢��");
			}else if(orders.getPaytype() == Orders.PAYTYPE_ALIPAY){
				orders.setPhone("֧����");
			}else{
				orders.setPhone("����");
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
			String export="�������#id,�������#total,��Ʒ����#dingdanInfo,�ջ���Ϣ#address,����״̬#name,֧����ʽ#phone,�����û�#username";//�˴�Ϊ���⣬excel���е�title�����մ˸�ʽ���ɣ���ʽ����Ķ������ǿ������ӻ��߼�����Ŀ��
			String[] excelHeader = export.split(",");
			String fileName="����" + sdf.format(new Date()); //className:���ɵ�excelĬ���ļ�����sheetҳ
			ExcelUtils.export(response, fileName, excelHeader, orderList);//���÷�װ�õĵ������������巽��������
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
