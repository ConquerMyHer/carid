package com.ldblock.carid.controller;

import java.util.List;

import javax.validation.Valid;

import com.ldblock.carid.config.security.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldblock.carid.data.bo.CarOwnerAddBo;
import com.ldblock.carid.data.bo.CarOwnerQuery;
import com.ldblock.carid.data.po.CarOwner;
import com.ldblock.carid.service.CarOwnerService;

@RestController
@RequestMapping("/car-owner")
public class CarOwnerController {

	@Autowired
	CarOwnerService carOwnerService;
	
	@PostMapping
	public CarOwner add(@RequestBody @Valid CarOwnerAddBo carOwnerAddBo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) authentication.getPrincipal();
		return carOwnerService.insert(carOwnerAddBo, userContext.getId());
	}

//	@GetMapping("/info")
	@GetMapping
	public CarOwner getCarOwner(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) authentication.getPrincipal();
		return carOwnerService.getByOwnerId(userContext.getId());
	}
	
}
