package com.ldblock.carid.service;

import com.ldblock.carid.dao.AppAuthMapper;
import com.ldblock.carid.dao.AppInfoMapper;
import com.ldblock.carid.data.bo.AppAuthCodeBo;
import com.ldblock.carid.data.vo.AuthCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppAuthCodeService {
    @Autowired
    AppAuthMapper appAuthMapper;


//    @Cacheable(cacheNames = "cache_auth_code", key = "#userId.toString()")
    public AuthCodeResponse getAuthCode(AppAuthCodeBo appAuthCodeBo, Integer userId) {
        List<String> authCodeRequestList = appAuthCodeBo.parseAuthRequest();
        List<String> validAuthority = new ArrayList<>();
//        AuthCodeResponse authCodeResponse = new AuthCodeResponse();
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
            // 将validAuthority转化为String
            String authorities = String.join(",", validAuthority);
            // 将appid, authCode和validAuthority存入redis缓存
//            String validAuthorities = cacheAuthCode(userId.toString(), authCode, authorities);
            // 将authCode和validAuthority返回给前端
            return new AuthCodeResponse(authCode, validAuthority);
//            return "success";
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
