package com.ldblock.carid.config.security.jwt.factory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ldblock.carid.config.security.AccessTokenContext;
import com.ldblock.carid.config.security.OpenContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ldblock.carid.config.security.UserContext;
import com.ldblock.carid.config.security.jwt.JwtProperties;
import com.ldblock.carid.data.po.User;

@Component
public class JwtTokenFactory {

    private JwtProperties jwtProperties;

    @Autowired
    public JwtTokenFactory(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    //生成token
    public String createAccessJwtToken(UserContext userContext) {
        //TODO：
        String token = JWT.create()
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusMinutes(jwtProperties.getTokenExpirationTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                //设置过期时间
                .withSubject(userContext.getUserName())
                .withClaim("user", JSON.toJSONString(userContext))
                .withIssuer(jwtProperties.getTokenIssuer())
                .sign(Algorithm.HMAC256(jwtProperties.getTokenSigningKey()));
        //使用HMAC算法，111111作为密钥加密

        return token;
    }

    // 生成AccessTokenContext对应的JwtToken
    public String createAccessJwtToken(AccessTokenContext accessTokenContext) {
        String token = JWT.create()
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusMinutes(jwtProperties.getTokenExpirationTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                //设置过期时间
                .withSubject(accessTokenContext.getOpenId())
                .withClaim("openid", JSON.toJSONString(accessTokenContext))
                .withIssuer(jwtProperties.getTokenIssuer())
                .sign(Algorithm.HMAC256(jwtProperties.getTokenSigningKey()));
        //使用HMAC算法，111111作为密钥加密

        return token;
    }

    //生成刷新token
    public String createRefreshToken(UserContext userContext) {
        String token = JWT.create()
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusMinutes(jwtProperties.getRefreshTokenExpTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                //设置过期时间
                .withSubject(userContext.getUserName())
                .withClaim("user", JSON.toJSONString(userContext))
                .withIssuer(jwtProperties.getTokenIssuer())
                .sign(Algorithm.HMAC256(jwtProperties.getTokenSigningKey()));
        //使用HMAC算法，111111作为密钥加密
        return token;
    }

    // 生成AccessTokenContext对应的RefreshToken
    public String createRefreshToken(AccessTokenContext accessTokenContext) {
        String token = JWT.create()
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusMinutes(jwtProperties.getRefreshTokenExpTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                //设置过期时间
                .withSubject(accessTokenContext.getOpenId())
                .withClaim("openid", JSON.toJSONString(accessTokenContext))
                .withIssuer(jwtProperties.getTokenIssuer())
                .sign(Algorithm.HMAC256(jwtProperties.getTokenSigningKey()));
        //使用HMAC算法，111111作为密钥加密
        return token;
    }


    // 解析token
    public OpenContext parseToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtProperties.getTokenSigningKey()))
                    .withIssuer(jwtProperties.getTokenIssuer())
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            String subject = jwt.getSubject();
            String openid = jwt.getClaim("openid").asString();
            OpenContext openContext = new OpenContext();
            openContext.setOpenid(openid);
            openContext.setUserName(subject);
            return openContext;
        } catch (JWTVerificationException e) {
            throw new RuntimeException("token无效");
        }
    }
}
