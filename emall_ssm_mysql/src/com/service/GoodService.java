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
 * ��Ʒ����
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
	 * ��ȡ����
	 * @return
	 */
	public int getCount(){
		return goodDao.selectCount();
	}
	
	/**
	 * ��ȡ�б�
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getList(int page, int size){
		return packGood(goodDao.selectList(size * (page-1), size));
	}
	
	/**
	 * ��ȡ�б�
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListOrderSales(int page, int size){
		return packGood(goodDao.selectListOrderSales(size * (page-1), size));
	}
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public int getCountByIds(String ids){
		return Objects.nonNull(ids) ? goodDao.selectCountByIds(ids) : 0;
	}
	
	/**
	 * ��ȡ�б�
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByIds(String ids, int page, int size){
		return Objects.nonNull(ids) ? packGood(goodDao.selectListByIds(ids, size * (page-1), size)) : null;
	}
	
	/**
	 * ͨ�����ƻ�ȡ��Ʒ����
	 * @return
	 */
	public long getTotalByName(String name){
		return goodDao.selectCountByName(name);
	}
	
	/**
	 * ͨ�����ƻ�ȡ��Ʒ�б�
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByName(String name, int page, int size){
		return packGood(goodDao.selectListByName(name, size * (page-1), size));
	}
	
	/**
	 * ��ȡ����
	 * @param typeid
	 * @return
	 */
	public long getCountByType(int typeId){
		return typeId > 0 ? goodDao.selectCountByTypeId(typeId) : this.getCount();
	}

	/**
	 * ͨ����������
	 * @param typeid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByType(int typeId, int page, int size) {
		return typeId > 0 ? packGood(goodDao.selectListByTypeId(typeId, size * (page-1), size)) : this.getList(page, size);
	}
	
	/**
	 * ��ȡ����
	 * @param typeid
	 * @return
	 */
	public long getCountByTopType(byte type){
		return goodDao.selectCountByTopType(type);
	}
	
	/**
	 * ͨ���Ƽ�����
	 * @param typeid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByTopType(byte type, int page, int size) {
		return packGood(goodDao.selectListByTopType(type, size * (page-1), size));
	}
	
	/**
	 * ͨ��id��ȡ
	 * @param id
	 * @return
	 */
	public Goods get(int id) {
		return packGood(goodDao.select(id));
	}
	
	/**
	 * ���
	 * @param good
	 */
	public boolean add(Goods good) {
		return goodDao.insert(good);
	}

	/**
	 * �޸�
	 * @param good
	 * @return 
	 */
	public boolean update(Goods good) {
		return goodDao.update(good);
	}
	
	/**
	 * �޸Ŀ��
	 * @return 
	 */
	public boolean updateStock(int id, int stock) {
		return goodDao.updateStock(id, stock);
	}
	
	/**
	 * �޸�����
	 * @return 
	 */
	public boolean updateSales(int id, int sales) {
		return goodDao.updateSales(id, sales);
	}
	
	/**
	 * ɾ����Ʒ
	 * @param good
	 */
	@Transactional // ��������
	public boolean delete(int id) {
		topService.deleteByGoodId(id); // ��ɾ������Ʒ���Ƽ���Ϣ
		return goodDao.delete(id);
	}
	

	/**
	 * ��װ��Ʒ
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
	 * ��װ��Ʒ
	 * @param good
	 * @return
	 */
	private Goods packGood(Goods good) {
		if(good != null) {
			good.setType(typeService.get(good.getTypeId())); // ��Ŀ��Ϣ
			good.setTop(Objects.nonNull(topService.getByGoodIdAndType(good.getId(), Tops.TYPE_TODAY)));
		}
		return good;
	}

}