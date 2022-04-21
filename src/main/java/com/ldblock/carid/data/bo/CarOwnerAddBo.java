package com.ldblock.carid.data.bo;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CarOwnerAddBo {

//	@NotBlank(message="车主姓名不能为空")
//	private String ownerName;

	@NotBlank(message="Vin码不能为空")
	private String vinCode;

	@NotBlank(message="车牌号不能为空")
	private String carLicenseNumber;
	
}
