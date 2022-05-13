package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AdminsDao;
import com.entity.Admins;
import com.util.SafeUtil;//ʹ�������ļ����㷨DES��ʵ����վ��Ϣ�ļ�������ܳ���

/**
 * ����Ա����
 */
@Service
public class AdminService {

	@Autowired
	private AdminsDao adminDao;
	
	
	/**
	 * ����
	 * @return
	 */
	public long getCount() {
		return adminDao.selectCount();
	}

	/**
	 * �б�
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Admins> getList(int page, int rows) {
		return adminDao.selectList(rows * (page-1), rows);
	}

	/**
	 * ͨ��id��ѯ
	 * @param id
	 * @return
	 */
	public Admins get(int id) {
		return adminDao.select(id);
	}
	
	/**
	 * ͨ���û�����ȡ
	 * @param username
	 * @return
	 */
	public Admins getByUsername(String username) {
		return adminDao.selectByUsername(username);
	}
	
	/**
	 * ͨ���û��������ȡ
	 * @param username
	 * @param password
	 * @return
	 */
	public Admins getByUsernameAndPassword(String username, String password){
		return adminDao.selectByUsernameAndPassword(username, SafeUtil.encode(password));
	}
	
	/**
	 * ���
	 * @param admin
	 */
	public boolean add(Admins admin) {
		admin.setPassword(SafeUtil.encode(admin.getPassword()));
		return adminDao.insert(admin);
	}
	
	/**
	 * ����
	 * @param user
	 */
	public boolean update(int id, String password) {
		return adminDao.update(id, SafeUtil.encode(password));
	}

	/**
	 * ɾ��
	 * @param user
	 */
	public boolean delete(int id) {
		return adminDao.delete(id);
	}

	
}
