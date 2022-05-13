package com.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.TopsDao;
import com.entity.Tops;

/**
 * 商品推荐服务
 */
@Service
public class

TopService {

	@Autowired	
	private TopsDao topDao;


	/**
	 * 获取推荐商品ID列表
	 * @param type
	 */
	public String getGoodIdsByType(byte type) {
		List<String> list = topDao.selectGoodIdsByType(type);
		return Objects.nonNull(list) ? String.join(",", list) : null;
	}

	/**
	 * 通过商品ID和类型获取
	 * @param goodId
	 * @param type
	 * @return
	 */
	public Tops getByGoodIdAndType(int goodId, byte type) {
		return topDao.selectByGoodIdAndType(goodId, type);
	}
	
	/**
	 * 添加
	 * @param top
	 * @return
	 */
	public boolean add(int goodId, byte type) {
		Tops top = new Tops();
		top.setGoodId(goodId);
		top.setType(type);
		return topDao.insert(top);
	}

	/**
	 * 删除
	 * @param top
	 */
	public boolean delete(int goodId, byte type) {
		return topDao.deleteByGoodIdAndType(goodId, type);
	}
	
	/**
	 * 按商品删除
	 * @param goodId
	 * @return
	 */
	public boolean deleteByGoodId(int goodId) {
		return topDao.deleteByGoodId(goodId);
	}
	
}
