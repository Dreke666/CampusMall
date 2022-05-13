package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.entity.Carts;

public interface CartDao {

	@Select("select count(*) from carts where user_id=#{userId}")
	int selectCountByUserId(@Param("userId")int userId);
	
	@Select("select ifnull(sum(amount),0) from carts where user_id=#{userId}")
	int selectSumAmountByUserId(@Param("userId")int userId);
	
	@Select("select * from carts where user_id=#{userId}")
	List<Carts> selectListByUserId(@Param("userId")int userId);
    
    @Select("select * from carts where id=#{id}")
    Carts select(@Param("id")int id);
    
    @Select("select * from carts where user_id=#{userId} and good_id=#{goodId} limit 1")
    Carts selectByUserIdAndGoodId(@Param("userId")int userId, @Param("goodId")int goodId);

    @Delete("delete from carts where user_id=#{goodId}")
	boolean deleteByUserid(int goodId);
    
	@Insert("insert into carts (amount,good_id,user_id) values (#{amount},#{goodId},#{userId})")
	@SelectKey(keyProperty="id",statement="SELECT LAST_INSERT_ID()", before=false, resultType=Integer.class)  
    public boolean insert(Carts carts);
	
	@Update("update carts set amount=amount+#{amount} where id=#{id}")
	public boolean updateAmonut(@Param("id")int id, @Param("amount")int amount);
	
	@Update("delete from carts where id=#{id}")
	public boolean delete(int id);
	
	@Update("delete from carts where user_id=#{userId}")
	public boolean deleteByUserId(int userId);
}