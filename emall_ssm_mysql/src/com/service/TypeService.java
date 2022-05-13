package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.TypesDao;
import com.entity.Types;

/**
 * ���ͷ���
 */
@Service
public class TypeService {

	@Autowired	
	private TypesDao typeDao;
	
	
	/**
	 * ��ȡ�б�
	 * @return
	 */
	public List<Types> getList(){
		return typeDao.selectList();
	}

	/**
	 * ͨ��id��ѯ
	 * @param id
	 * @return
	 */
	public Types get(int id) {
		return typeDao.select(id);
	}
	
	/**
	 * ���
	 * @param type
	 * @return
	 */
	public boolean add(Types type) {
		return typeDao.insert(type);
	}

	/**
	 * ����
	 * @param type
	 */
	public boolean update(Types type) {
		return typeDao.update(type);
	}

	/**
	 * ɾ��
	 * @param type
	 */
	public boolean delete(int id) {
		return typeDao.delete(id);
	}
	
}
