package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UsersDao;
import com.entity.Users;
import com.util.SafeUtil;

/**
 * �û�����
 */
@Service
public class UserService {

	@Autowired
	private UsersDao userDao;
	
	
	/**
	 * ����
	 * @return
	 */
	public long getCount() {
		return userDao.selectCount();
	}
	
	/**
	 * �б�
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Users> getList(int page, int rows) {
		return userDao.selectList(rows * (page-1), rows);
	}
	
	/**
	 * ͨ��id��ȡ
	 * @param id
	 * @return
	 */
	public Users get(int id){
		return userDao.select(id);
	}
	
	/**
	 * ͨ���û�����ȡ
	 * @param username
	 * @return
	 */
	public Users getByUsername(String username){
		return userDao.selectByUsername(username);
	}
	
	/**
	 * ͨ���û����������ȡ
	 * @param username
	 * @param password
	 * @return
	 */
	public Users getByUsernameAndPassword(String username, String password){
		return userDao.selectByUsernameAndPassword(username, SafeUtil.encode(password));
	}

	/**
	 * ���
	 * @param user
	 * @return
	 */
	public boolean add(Users user) {
		user.setPassword(SafeUtil.encode(user.getPassword()));
		return userDao.insert(user);
	}

	/**
	 * ����
	 * @param user
	 */
	public boolean update(int id, String name, String phone, String address) {
		Users user = new Users();
		user.setId(id);
		user.setName(name);
		user.setPhone(phone);
		user.setAddress(address);
		return userDao.update(user);
	}
	
	/**
	 * ����
	 * @param user
	 */
	public boolean updatePassword(int id, String password) {
		return userDao.updatePassword(id, SafeUtil.encode(password));
	}

	/**
	 * ɾ��
	 * @param id
	 */
	public boolean delete(int id) {
		return userDao.delete(id);
	}

	public int updatePoint(Users users) {
		return userDao.updatePoint(users);
	}
}
