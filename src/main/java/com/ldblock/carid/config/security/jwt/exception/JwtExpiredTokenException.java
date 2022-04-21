package com.ldblock.carid.config.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8934006632852411067L;

	public JwtExpiredTokenException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
