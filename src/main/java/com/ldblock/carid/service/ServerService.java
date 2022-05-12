package com.ldblock.carid.service;

import com.ldblock.carid.config.security.OpenContext;
import com.ldblock.carid.config.security.jwt.factory.JwtTokenFactory;
import com.ldblock.carid.dao.CarOwnerMapper;
import com.ldblock.carid.dao.OpenPlatformInfoMapper;
import com.ldblock.carid.data.bo.CheckVINBo;
import com.ldblock.carid.data.po.CarOwner;
import com.ldblock.carid.data.po.OpenPlatformInfo;
import com.ldblock.carid.data.vo.ServerCheckVINResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ServerService {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    JwtTokenFactory jwtTokenFactory;

    @Autowired
    OpenPlatformInfoMapper openPlatformInfoMapper;

    @Autowired
    CarOwnerMapper carOwnerMapper;

    public ServerCheckVINResponse checkVIN(CheckVINBo checkVINBo) {
        OpenContext openContext = jwtTokenFactory.parseToken(checkVINBo.getAccessToken());
        String openid = openContext.getUserName();
        OpenPlatformInfo openPlatformInfo = openPlatformInfoMapper.getOpenPlatformInfoByOpenIdAndAppId(openid, checkVINBo.getAppId());
        // 在carOwnerMapper中查询是否有记录
        CarOwner carOwner = carOwnerMapper.selectByOwnerId(openPlatformInfo.getUserId());
        ServerCheckVINResponse response = new ServerCheckVINResponse();
        String vin = checkVINBo.getVin();
        String vinCode = carOwner.getVinCode();
        response.setRes(Objects.equals(vin, vinCode));
        return response;
    }
}
