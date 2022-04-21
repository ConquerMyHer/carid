package com.ldblock.carid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldblock.carid.config.security.UserContext;
import com.ldblock.carid.data.bo.LoginBo;
import com.ldblock.carid.data.vo.UserVo;

import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	MapperFacade orikaMapper;
	
	@PostMapping("/login")
	public void login(@RequestBody LoginBo loginBo) {
		//FIXME: 此接口毫无用处，也永远不会被调用，仅仅是用于swagger生成页面
	}
	
	@GetMapping
	public UserVo getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) authentication.getPrincipal();
		return orikaMapper.map(userContext, UserVo.class);
	}
	
}
