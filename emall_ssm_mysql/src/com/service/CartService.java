package com.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CartDao;
import com.entity.Carts;

/**
 * 购物车服务
 */
@Service
public class CartService {

	@Autowired
	private CartDao cartDao;
	@Autowired
	private GoodService goodService ;
	
	
	/**
	 * 获取购物车总数
	 * @param userId
	 */
	public int getCount(int userId) {
		return cartDao.selectSumAmountByUserId(userId);
	}

	/**
	 * 获取总金额
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
	 * 获取购物车列表
	 * @param userId
	 * @return
	 */
	public List<Carts> getList(int userId){
		return pack(cartDao.selectListByUserId(userId));
	}
	
	/**
	 * 添加购物车
	 * @return
	 */
	public boolean save(int goodId, int userId) {
		Carts cart = cartDao.selectByUserIdAndGoodId(userId, goodId);
		if(Objects.nonNull(cart)) { // 如果存在记录 数量+1
			return cartDao.updateAmonut(cart.getId(), 1);
		}
		cart = new Carts();
		cart.setGoodId(goodId);
		cart.setUserId(userId);
		cart.setAmount(1); // 默认数量1
		return cartDao.insert(cart);
	}

	
	/**
	 * 数量+1
	 * @param id
	 * @return
	 */
	public boolean add(int id) {
		return cartDao.updateAmonut(id, 1);
	}
	
	
	/**
	 * 数量-1
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
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		return cartDao.delete(id);
	}
	
	/**
	 * 清空购物车
	 * @param userId
	 * @return
	 */
	public boolean clean(Integer userId) {
		return cartDao.deleteByUserId(userId);
	}
	

	/**
	 * 封装
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
