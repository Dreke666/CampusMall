package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.entity.Items;

public interface ItemsDao {
	
    @Select("select * from items where order_id=#{orderId}")
	public List<Items> selectList(int orderId);
    
	@Insert("insert into items (price,amount,order_id,good_id) values (#{price},#{amount},#{orderId},#{goodId})")
	@SelectKey(keyProperty="id",statement="SELECT LAST_INSERT_ID()", before=false, resultType=Integer.class)  
    public boolean insert(Items items);

}