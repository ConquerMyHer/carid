package com.ldblock.carid.service;

import com.ldblock.carid.config.security.AccessTokenContext;
import com.ldblock.carid.config.security.jwt.factory.JwtTokenFactory;
import com.ldblock.carid.dao.AppAuthMapper;
import com.ldblock.carid.dao.AppInfoMapper;
import com.ldblock.carid.dao.OpenPlatformInfoMapper;
import com.ldblock.carid.data.bo.AccessTokenBo;
import com.ldblock.carid.data.po.AppInfo;
import com.ldblock.carid.data.vo.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AppInfoMapper appInfoMapper;
    @Autowired
    OpenPlatformInfoMapper openPlatformInfoMapper;

    @Autowired
    JwtTokenFactory jwtTokenFactory;

    @Autowired private AuthenticationSuccessHandler successHandler;

    public AccessTokenResponse getAccessToken(AccessTokenBo accessTokenBo){
        // 验证AppId与Secret是否正确
        AppInfo appInfo = appInfoMapper.selectByAppIdAndSecret(accessTokenBo.getAppId(), accessTokenBo.getSecret());
        if (appInfo == null) {
            // 直接返回空
            // 控制台输出
            System.out.println("AppId或Secret错误：" + accessTokenBo.getAppId() + "," + accessTokenBo.getSecret());
            return new AccessTokenResponse();
        }
        else {
            // 从Redis中使用"cache_auth_code:" + appAuthCodeBo.getAppId() + ":" + authCode作为key，获取UserId
            String userId = (String) redisTemplate.opsForValue().get("cache_auth_code:" + accessTokenBo.getAppId() + ":" + accessTokenBo.getCode());
            if (userId == null) {
                // 直接返回空
                // 控制台输出
                System.out.println("AuthCode错误");
                return new AccessTokenResponse();
            }
            else {

                // 从OpenPlatformInfo表中获取OpenId
                String openId = openPlatformInfoMapper.getOpenIdByUserIdAndAppId(userId, accessTokenBo.getAppId());
                // TODO: 判断openId是否为空, 不应为空
                // 构造AccessTokenContext
                AccessTokenContext accessTokenContext = new AccessTokenContext();
                accessTokenContext.setOpenId(openId);
                accessTokenContext.setWhateverString("whateverString");
                // 使用JWT生成AccessToken以及RefreshToken
                String accessToken = jwtTokenFactory.createAccessJwtToken(accessTokenContext);
                String refreshToken = jwtTokenFactory.createRefreshToken(accessTokenContext);
                System.out.println("AccessToken:" + accessToken);
                System.out.println("RefreshToken:" + refreshToken);
//                AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
//                accessTokenResponse.setAccessToken(accessToken);
//                accessTokenResponse.setRefreshToken(refreshToken);
//                accessTokenResponse.setRefreshToken(openId);
//                return accessTokenResponse;
                return new AccessTokenResponse(openId,accessToken,refreshToken);
            }
        }
    }
}
