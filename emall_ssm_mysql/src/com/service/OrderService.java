package com.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.config.ExceptionConfig;
import com.config.ExceptionConfig.MyException;
import com.dao.ItemsDao;
import com.dao.OrdersDao;
import com.entity.Carts;
import com.entity.Goods;
import com.entity.Items;
import com.entity.Orders;

/**
 * ��Ʒ��������
 */
@Service
public class OrderService {

	@Autowired
	private OrdersDao orderDao;
	@Autowired
	private ItemsDao itemDao;
	@Autowired
	private GoodService goodService;
	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;
	
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public int getCount(byte status) {
		return status>0 ? orderDao.selectCountByStatus(status) : orderDao.selectCount();
	}
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public int getCountByUserid(int userId) {
		return orderDao.selectCountByUserid(userId);
	}
	
	/**
	 * ��ȡ�����б�
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Orders> getList(byte status, int page, int size) {
		return pack(status>0 ? orderDao.selectListByStatus(status, size*(page-1), size) : orderDao.selectList(size *(page-1), size));
	}
	
	/**
	 * ��ȡĳ�û�ȫ������
	 * @param userId
	 */
	public List<Orders> getListByUserid(int userId, int page, int size) {
		return pack(orderDao.selectListByUserid(userId, size*(page-1), size));
	}

	/**
	 * ͨ��id��ȡ
	 * @param id
	 * @return
	 */
	public Orders get(int id) {
		return pack(orderDao.select(id));
	}
	
	/**
	 * ���涩��
	 * @param order
	 * @throws MyException 
	 */
	@Transactional
	public int add(int goodId, int userId) throws MyException {
		Goods goods = goodService.get(goodId);
		if(goods.getStock() < 1) { // ��֤���
			throw new ExceptionConfig.MyException("��Ʒ [ "+goods.getName()+" ] ��治��");
		}
		Orders order = new Orders();
		order.setTotal(goods.getPrice());
		order.setAmount(1);
		order.setUserId(userId);
		order.setStatus(Orders.STATUS_UNPAY);
		order.setSystime(new Date());
		orderDao.insert(order);
		int orderId = order.getId();
		Items item = new Items();
		item.setOrderId(orderId);
		item.setGoodId(goods.getId());
		item.setPrice(goods.getPrice());
		item.setAmount(1);
		itemDao.insert(item);
		return orderId;
	}
	
	/**
	 * ���涩��
	 * @param order
	 * @throws MyException 
	 */
	@Transactional
	public int save(int userId) throws MyException {
		List<Carts> cartList = cartService.getList(userId);
		if(Objects.isNull(cartList) || cartList.isEmpty()) {
			throw new ExceptionConfig.MyException("���ﳵû����Ʒ");
		}
		// ��֤���
		for(Carts cart : cartList){
			if(cart.getGood().getStock() < cart.getAmount()) { // ��֤���
				throw new ExceptionConfig.MyException("��Ʒ [ "+cart.getGood().getName()+" ] ��治��");
			}
			goodService.updateStock(cart.getGood().getId(), cart.getAmount()); // �����
			goodService.updateSales(cart.getGood().getId(), cart.getAmount()); // ������
		}
		int total = 0; // �����ܼ�
		for(Carts cart : cartList) {
			total += cart.getGood().getPrice() * cart.getAmount();
		}
		Orders order = new Orders();
		order.setUserId(userId);
		order.setTotal(total);
		order.setAmount(cartList.size());
		order.setStatus(Orders.STATUS_UNPAY);
		order.setSystime(new Date());
		orderDao.insert(order);
		int orderId = order.getId();
		for(Carts cart : cartList){
			Items item = new Items();
			item.setOrderId(orderId);
			item.setGoodId(cart.getGoodId());
			item.setPrice(cart.getGood().getPrice());
			item.setAmount(cart.getAmount());
			itemDao.insert(item);
		}
		// ��չ��ﳵ
		cartService.clean(userId);
		return orderId;
	}
	
	/**
	 * ����֧��
	 * @param order
	 */
	public void pay(Orders order) {
		Orders old = orderDao.select(order.getId());
		old.setStatus(Orders.STATUS_PAYED);
		old.setPaytype(order.getPaytype());
		old.setName(order.getName());
		old.setPhone(order.getPhone());
		old.setAddress(order.getAddress());
		orderDao.update(old);
	}

	/**
	 * ��������
	 * @param id
	 * @return 
	 */
	public boolean send(int id) {
		return orderDao.updateStatus(id, Orders.STATUS_SEND);
	}
	
	/**
	 * �������
	 * @param id
	 * @return 
	 */
	public boolean finish(int id) {
		return orderDao.updateStatus(id, Orders.STATUS_FINISH);
	}

	/**
	 * ɾ������
	 * @param id
	 */
	public boolean delete(int id) {
		return orderDao.delete(id);
	}
	
	/**
	 * ��װ
	 * @param order
	 * @return
	 */
	private List<Orders> pack(List<Orders> list) {
		if(Objects.nonNull(list) && !list.isEmpty()) {
			for(Orders order : list) {
				order = pack(order);
			}
		}
		return list;
	}

	/**
	 * ��װ
	 * @param order
	 * @return
	 */
	private Orders pack(Orders order) {
		if(Objects.nonNull(order)) {
			List<Items> itemList = itemDao.selectList(order.getId());
			for(Items item : itemList) {
				item.setGood(goodService.get(item.getGoodId()));
			}
			order.setItemList(itemList);
			order.setUser(userService.get(order.getUserId()));
		}
		return order;
	}

	public List<Orders> getAllList() {
		return pack(orderDao.selectAllList());
	}
}
