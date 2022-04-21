package com.ldblock.carid.config.security.jwt;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.ldblock.carid.config.security.UserContext;
import com.ldblock.carid.config.security.jwt.token.RawAccessJwtToken;
import com.ldblock.carid.data.po.User;

/**
 * An {@link AuthenticationProvider} implementation that will use provided
 * instance of {@link JwtToken} to perform authentication.
 * 
 * @author vladimir.stankovic
 *
 * Aug 5, 2016
 */
@Component
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtProperties jwtProperties;
    
    @Autowired
    public JwtAuthenticationProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //取出凭证
    	RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

        Map<String, Claim> claims = rawAccessToken.parseClaims(jwtProperties.getTokenSigningKey());
//        String subject = jwsClaims.getBody().getSubject();
//        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
//        List<GrantedAuthority> authorities = scopes.stream()
//            .map(SimpleGrantedAuthority::new)
//            .collect(Collectors.toList());
        
//        User context = User.create(subject, authorities);
        UserContext userContext = JSON.parseObject(claims.get("user").asString(), UserContext.class);
        
        //生成认证的token
        return new JwtAuthenticationToken(userContext, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
