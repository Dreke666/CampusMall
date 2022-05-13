package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;//在mybatis的Map类中，方法参数为多个时，前面一般需要加上@Param注解
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.entity.Users;

public interface UsersDao {
    
	@Select("select count(*) from users")
	public long selectCount();
	
    @Select("select * from users order by id desc limit #{begin}, #{size}")
	public List<Users> selectList(@Param("begin")int begin, @Param("size")int size);
	
    @Select("select * from users where id=#{id}")
	public Users select(int id);
    
    @Select("select * from users where username=#{username}")
    public Users selectByUsername(String username);
	
    @Select("select * from users where username=#{username} and password=#{password}")
	public Users selectByUsernameAndPassword(@Param("username")String username, @Param("password")String password);
    
	@Insert("insert into users (username,password,name,phone,address) "
			+ "values (#{username},#{password},#{name},#{phone},#{address})")
	@SelectKey(keyProperty="id",statement="SELECT LAST_INSERT_ID()", before=false, resultType=Integer.class)  
    public boolean insert(Users user);
	
	@Update("update users set name=#{name},phone=#{phone},address=#{address} where id=#{id}")
	public boolean update(Users user);
	
	@Update("update users set password=#{password} where id=#{id}")
	public boolean updatePassword(@Param("id")int id, @Param("password")String password);
	
	@Update("delete from users where id=#{id}")
	public boolean delete(int id);

	@Update("update users set point=#{point} where id=#{id}")
	int updatePoint(Users users);
}