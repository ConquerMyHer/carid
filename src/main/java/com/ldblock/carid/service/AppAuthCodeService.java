package com.ldblock.carid.service;

import com.ldblock.carid.dao.AppAuthMapper;
import com.ldblock.carid.dao.AppInfoMapper;
import com.ldblock.carid.dao.OpenPlatformInfoMapper;
import com.ldblock.carid.data.bo.AppAuthCodeBo;
import com.ldblock.carid.data.po.OpenPlatformInfo;
import com.ldblock.carid.data.vo.AuthCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AppAuthCodeService {
    @Autowired
    AppAuthMapper appAuthMapper;
    @Autowired
    OpenPlatformInfoMapper openPlatformInfoMapper;
    @Autowired
    RedisTemplate redisTemplate;


//    @Cacheable(cacheNames = "cache_auth_code", key = "#userId.toString()")
    public AuthCodeResponse getAuthCode(AppAuthCodeBo appAuthCodeBo, String userId) {
        List<String> authCodeRequestList = appAuthCodeBo.parseAuthRequest();
        List<String> validAuthority = new ArrayList<>();
        // 遍历authCodeRequestList，从数据库中查询是否存在该appId对应的authCode
        for (String authCodeRequest : authCodeRequestList) {
            String authority = appAuthMapper.selectAuth(appAuthCodeBo.getAppId(), authCodeRequest);
            if (authority != null) {
                // 向validAuthority中添加authority
                validAuthority.add(authority);
            }
        }
        // 判断validAuthority是否为空
        if (validAuthority.size() != 0) {
            // 使用uuid生成authCode
            String authCode = appAuthCodeBo.generateAuthCode();
            // 以cache_auth_code:AppId:authCode为key，userId为value存入redis
            redisTemplate.boundValueOps("cache_auth_code:" + appAuthCodeBo.getAppId() + ":" + authCode).set(userId, 5, TimeUnit.MINUTES);
//            redisTemplate.expire("cache_auth_code:" + appAuthCodeBo.getAppId() + ":" + authCode, 5, TimeUnit.MINUTES);
//            redisTemplate.opsForValue().set("cache_auth_code:" + appAuthCodeBo.getAppId() + ":" + authCode, userId);
            // 检查在openPlatformInfo表中是否已经存在openId
            String openId = openPlatformInfoMapper.getOpenIdByUserIdAndAppId(userId, appAuthCodeBo.getAppId());
            // 检查在openPlatformInfo表中是否已经存储有scope，TODO:暂时不考虑scope变动的比较
            String scope = openPlatformInfoMapper.getScopeByUserIdAndAppId(userId, appAuthCodeBo.getAppId());
            // 将validAuthority转化为String
            String authorities = String.join(",", validAuthority);
            if (openId == null) {
                // 如果不存在，则生成openId
                openId = appAuthCodeBo.generateAuthCode();
                // 插入openPlatformInfo
                openPlatformInfoMapper.insertOpenPlatformInfo(userId, appAuthCodeBo.getAppId(), openId, authorities);
            }
            else {
                // 如果存在，则更新openPlatformInfo
                openPlatformInfoMapper.updateOpenPlatformInfo(userId, appAuthCodeBo.getAppId(), openId, authorities);
            }

            // 将authCode和validAuthority返回给前端
            return new AuthCodeResponse(authCode, validAuthority);
        }
        else {
//            return "fail";
            return new AuthCodeResponse();
        }

    }

//    @Cacheable(cacheNames = "cache_auth_code", key = "#authCode")
//    public String cacheAuthCode(String userIdToCache, String authCode, String validAuthorities) {
//        // 将appid, authCode和validAuthority存入redis缓存
//        return validAuthorities;
//    }
}
