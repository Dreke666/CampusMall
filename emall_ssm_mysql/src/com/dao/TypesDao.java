package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.entity.Types;

public interface TypesDao {

    @Select("select * from types order by num")
	public List<Types> selectList();
    
    @Select("select * from types where id=#{id}")
    public Types select(int id);
    
	@Insert("insert into types (name,num) values (#{name},#{num})")
	@SelectKey(keyProperty="id",statement="SELECT LAST_INSERT_ID()", before=false, resultType=Integer.class)  
    public boolean insert(Types type);
	
	@Update("update types set name=#{name},num=#{num} where id=#{id}")
	public boolean update(Types type);
	
	@Update("delete from types where id=#{id}")
	public boolean delete(int id);
}