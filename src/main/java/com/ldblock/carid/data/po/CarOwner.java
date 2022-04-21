package com.ldblock.carid.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_car_owner")
public class CarOwner {

	@TableId(type=IdType.AUTO)
	private Long id;

//	private String ownerName; // 车主姓名

	private Integer ownerId; // 用户id

	private String carLicenseNumber; //车牌号

	private String vinCode; //VIN码


}
