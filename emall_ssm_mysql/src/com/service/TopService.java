package com.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.TopsDao;
import com.entity.Tops;

/**
 * ��Ʒ�Ƽ�����
 */
@Service
public class

TopService {

	@Autowired	
	private TopsDao topDao;


	/**
	 * ��ȡ�Ƽ���ƷID�б�
	 * @param type
	 */
	public String getGoodIdsByType(byte type) {
		List<String> list = topDao.selectGoodIdsByType(type);
		return Objects.nonNull(list) ? String.join(",", list) : null;
	}

	/**
	 * ͨ����ƷID�����ͻ�ȡ
	 * @param goodId
	 * @param type
	 * @return
	 */
	public Tops getByGoodIdAndType(int goodId, byte type) {
		return topDao.selectByGoodIdAndType(goodId, type);
	}
	
	/**
	 * ���
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
	 * ɾ��
	 * @param top
	 */
	public boolean delete(int goodId, byte type) {
		return topDao.deleteByGoodIdAndType(goodId, type);
	}
	
	/**
	 * ����Ʒɾ��
	 * @param goodId
	 * @return
	 */
	public boolean deleteByGoodId(int goodId) {
		return topDao.deleteByGoodId(goodId);
	}
	
}
