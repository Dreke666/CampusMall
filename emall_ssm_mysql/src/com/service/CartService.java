package com.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CartDao;
import com.entity.Carts;

/**
 * ���ﳵ����
 */
@Service
public class CartService {

	@Autowired
	private CartDao cartDao;
	@Autowired
	private GoodService goodService ;
	
	
	/**
	 * ��ȡ���ﳵ����
	 * @param userId
	 */
	public int getCount(int userId) {
		return cartDao.selectSumAmountByUserId(userId);
	}

	/**
	 * ��ȡ�ܽ��
	 * @param id
	 * @return
	 */
	public int getTotal(int userId) {
		int total = 0;
		List<Carts> cartList = this.getList(userId);
		if(Objects.nonNull(cartList) && ! cartList.isEmpty()) {
			for(Carts cart : cartList) {
				total += cart.getGood().getPrice() * cart.getAmount();
			}
		}
		return total;
	}
	
	/**
	 * ��ȡ���ﳵ�б�
	 * @param userId
	 * @return
	 */
	public List<Carts> getList(int userId){
		return pack(cartDao.selectListByUserId(userId));
	}
	
	/**
	 * ��ӹ��ﳵ
	 * @return
	 */
	public boolean save(int goodId, int userId) {
		Carts cart = cartDao.selectByUserIdAndGoodId(userId, goodId);
		if(Objects.nonNull(cart)) { // ������ڼ�¼ ����+1
			return cartDao.updateAmonut(cart.getId(), 1);
		}
		cart = new Carts();
		cart.setGoodId(goodId);
		cart.setUserId(userId);
		cart.setAmount(1); // Ĭ������1
		return cartDao.insert(cart);
	}

	
	/**
	 * ����+1
	 * @param id
	 * @return
	 */
	public boolean add(int id) {
		return cartDao.updateAmonut(id, 1);
	}
	
	
	/**
	 * ����-1
	 * @param id
	 * @return
	 */
	public boolean less(int id) {
		Carts cart = cartDao.select(id);
		if(cart.getAmount()<=1) {
			return delete(id);
		}
		return cartDao.updateAmonut(id, -1);
	}
	
	/**
	 * ɾ��
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		return cartDao.delete(id);
	}
	
	/**
	 * ��չ��ﳵ
	 * @param userId
	 * @return
	 */
	public boolean clean(Integer userId) {
		return cartDao.deleteByUserId(userId);
	}
	

	/**
	 * ��װ
	 * @param list
	 * @return
	 */
	private List<Carts> pack(List<Carts> list) {
		for(Carts cart : list) {
			cart = pack(cart);
		}
		return list;
	}

	private Carts pack(Carts cart) {
		if(Objects.nonNull(cart)) {
			cart.setGood(goodService.get(cart.getGoodId()));
			cart.setTotal(cart.getAmount() * cart.getGood().getPrice());
		}
		return cart;
	}
	
}
