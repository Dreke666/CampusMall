package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.entity.Tops;

public interface TopsDao {
    
	@Select("select good_id from tops where type=#{type}")
	public List<String> selectGoodIdsByType(byte type);
	
	@Select("select * from tops where good_id=#{goodId} and type=#{type}")
	public Tops selectByGoodIdAndType(@Param("goodId")int goodId, @Param("type")byte type);
	
	@Insert("insert into tops (type,good_id) values (#{type},#{goodId})")
	@SelectKey(keyProperty="id",statement="SELECT LAST_INSERT_ID()", before=false, resultType=Integer.class)  
    public boolean insert(Tops top);
	
	@Update("delete from tops where id=#{id}")
	public boolean delete(int id);
	
	@Delete("delete from tops where good_id=#{goodId}")
	public boolean deleteByGoodId(@Param("goodId")int goodId);
	
	@Delete("delete from tops where good_id=#{goodId} and type=#{type}")
	public boolean deleteByGoodIdAndType(@Param("goodId")int goodId, @Param("type")byte type);
}