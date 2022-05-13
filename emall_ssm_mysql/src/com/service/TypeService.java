package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.TypesDao;
import com.entity.Types;

/**
 * 类型服务
 */
@Service
public class TypeService {

	@Autowired	
	private TypesDao typeDao;
	
	
	/**
	 * 获取列表
	 * @return
	 */
	public List<Types> getList(){
		return typeDao.selectList();
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	public Types get(int id) {
		return typeDao.select(id);
	}
	
	/**
	 * 添加
	 * @param type
	 * @return
	 */
	public boolean add(Types type) {
		return typeDao.insert(type);
	}

	/**
	 * 更新
	 * @param type
	 */
	public boolean update(Types type) {
		return typeDao.update(type);
	}

	/**
	 * 删除
	 * @param type
	 */
	public boolean delete(int id) {
		return typeDao.delete(id);
	}
	
}
