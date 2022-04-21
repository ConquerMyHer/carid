package com.ldblock.carid.dao;

import java.util.List;

import com.ldblock.carid.data.po.User;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldblock.carid.data.bo.CarOwnerQuery;
import com.ldblock.carid.data.po.CarOwner;
import org.apache.ibatis.annotations.Select;

public interface CarOwnerMapper extends BaseMapper<CarOwner>{

	List<CarOwner> query(@Param("query") CarOwnerQuery query);

	@Select("select * from t_car_owner where owner_id = #{ownerId} limit 1")
	CarOwner selectByOwnerId(@Param("ownerId") Integer ownerId);

}
