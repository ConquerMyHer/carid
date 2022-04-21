package com.ldblock.carid.config.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.ldblock.carid.data.po.User;
import com.ldblock.carid.service.UserService;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder encoder;
    private final UserService userService;

    @Autowired
    public LoginAuthenticationProvider(final UserService userService, final BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = userService.getByUserName(username);
        if(user == null) {
        	throw new UsernameNotFoundException("用户不存在: " + username);
        }
        if(!password.equals(user.getPassword())) {
        	throw new BadCredentialsException("用户名或密码错误");
        }
//使用加密的密码
//        if (!encoder.matches(password, user.getPassword())) {
//            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
//        }
//角色和权限的处理
//        if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
        
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
//                .collect(Collectors.toList());
//        
        UserContext userContext = UserContext.builder().id(user.getId()).userName(user.getUserName()).build();
        
        //认证成功设置token     
        return new UsernamePasswordAuthenticationToken(userContext, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
    	//判断token是否匹配可以被处理
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
