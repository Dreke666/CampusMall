package com.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.GoodsDao;
import com.entity.Goods;
import com.entity.Tops;

/**
 * 商品服务
 */
@Service
public class GoodService {

	@Autowired	
	private GoodsDao goodDao;
	@Autowired
	private TopService topService;
	@Autowired
	private TypeService typeService;
	
	
	/**
	 * 获取总数
	 * @return
	 */
	public int getCount(){
		return goodDao.selectCount();
	}
	
	/**
	 * 获取列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getList(int page, int size){
		return packGood(goodDao.selectList(size * (page-1), size));
	}
	
	/**
	 * 获取列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListOrderSales(int page, int size){
		return packGood(goodDao.selectListOrderSales(size * (page-1), size));
	}
	
	/**
	 * 获取总数
	 * @return
	 */
	public int getCountByIds(String ids){
		return Objects.nonNull(ids) ? goodDao.selectCountByIds(ids) : 0;
	}
	
	/**
	 * 获取列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByIds(String ids, int page, int size){
		return Objects.nonNull(ids) ? packGood(goodDao.selectListByIds(ids, size * (page-1), size)) : null;
	}
	
	/**
	 * 通过名称获取产品总数
	 * @return
	 */
	public long getTotalByName(String name){
		return goodDao.selectCountByName(name);
	}
	
	/**
	 * 通过名称获取产品列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByName(String name, int page, int size){
		return packGood(goodDao.selectListByName(name, size * (page-1), size));
	}
	
	/**
	 * 获取数量
	 * @param typeid
	 * @return
	 */
	public long getCountByType(int typeId){
		return typeId > 0 ? goodDao.selectCountByTypeId(typeId) : this.getCount();
	}

	/**
	 * 通过分类搜索
	 * @param typeid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByType(int typeId, int page, int size) {
		return typeId > 0 ? packGood(goodDao.selectListByTypeId(typeId, size * (page-1), size)) : this.getList(page, size);
	}
	
	/**
	 * 获取数量
	 * @param typeid
	 * @return
	 */
	public long getCountByTopType(byte type){
		return goodDao.selectCountByTopType(type);
	}
	
	/**
	 * 通过推荐搜索
	 * @param typeid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByTopType(byte type, int page, int size) {
		return packGood(goodDao.selectListByTopType(type, size * (page-1), size));
	}
	
	/**
	 * 通过id获取
	 * @param id
	 * @return
	 */
	public Goods get(int id) {
		return packGood(goodDao.select(id));
	}
	
	/**
	 * 添加
	 * @param good
	 */
	public boolean add(Goods good) {
		return goodDao.insert(good);
	}

	/**
	 * 修改
	 * @param good
	 * @return 
	 */
	public boolean update(Goods good) {
		return goodDao.update(good);
	}
	
	/**
	 * 修改库存
	 * @return 
	 */
	public boolean updateStock(int id, int stock) {
		return goodDao.updateStock(id, stock);
	}
	
	/**
	 * 修改销量
	 * @return 
	 */
	public boolean updateSales(int id, int sales) {
		return goodDao.updateSales(id, sales);
	}
	
	/**
	 * 删除商品
	 * @param good
	 */
	@Transactional // 开启事务
	public boolean delete(int id) {
		topService.deleteByGoodId(id); // 先删除此商品的推荐信息
		return goodDao.delete(id);
	}
	

	/**
	 * 封装商品
	 * @param list
	 * @return
	 */
	private List<Goods> packGood(List<Goods> list) {
		for(Goods good : list) {
			good = packGood(good);
		}
		return list;
	}

	/**
	 * 封装商品
	 * @param good
	 * @return
	 */
	private Goods packGood(Goods good) {
		if(good != null) {
			good.setType(typeService.get(good.getTypeId())); // 类目信息
			good.setTop(Objects.nonNull(topService.getByGoodIdAndType(good.getId(), Tops.TYPE_TODAY)));
		}
		return good;
	}

}