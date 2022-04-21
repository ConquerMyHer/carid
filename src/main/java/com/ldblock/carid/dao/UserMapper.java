package com.ldblock.carid.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldblock.carid.data.po.User;


public interface UserMapper extends BaseMapper<User>{

	@Select("select * from t_user where user_name = #{userName} limit 1")
	User getByUserName(@Param("userName") String userName);

}
