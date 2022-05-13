package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AdminsDao;
import com.entity.Admins;
import com.util.SafeUtil;//使用著名的加密算法DES来实现网站信息的加密与解密程序

/**
 * 管理员服务
 */
@Service
public class AdminService {

	@Autowired
	private AdminsDao adminDao;
	
	
	/**
	 * 总数
	 * @return
	 */
	public long getCount() {
		return adminDao.selectCount();
	}

	/**
	 * 列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Admins> getList(int page, int rows) {
		return adminDao.selectList(rows * (page-1), rows);
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	public Admins get(int id) {
		return adminDao.select(id);
	}
	
	/**
	 * 通过用户名获取
	 * @param username
	 * @return
	 */
	public Admins getByUsername(String username) {
		return adminDao.selectByUsername(username);
	}
	
	/**
	 * 通过用户名密码获取
	 * @param username
	 * @param password
	 * @return
	 */
	public Admins getByUsernameAndPassword(String username, String password){
		return adminDao.selectByUsernameAndPassword(username, SafeUtil.encode(password));
	}
	
	/**
	 * 添加
	 * @param admin
	 */
	public boolean add(Admins admin) {
		admin.setPassword(SafeUtil.encode(admin.getPassword()));
		return adminDao.insert(admin);
	}
	
	/**
	 * 更新
	 * @param user
	 */
	public boolean update(int id, String password) {
		return adminDao.update(id, SafeUtil.encode(password));
	}

	/**
	 * 删除
	 * @param user
	 */
	public boolean delete(int id) {
		return adminDao.delete(id);
	}

	
}
