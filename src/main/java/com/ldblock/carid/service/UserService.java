package com.ldblock.carid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldblock.carid.dao.UserMapper;
import com.ldblock.carid.data.po.User;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;
	
	public User getByUserName(String userName) {
		//从数据库中获取用户信息
		User user = userMapper.getByUserName(userName);
		return user; 
	}
	
}
