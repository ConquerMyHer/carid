package com.ldblock.carid.controller;

import com.ldblock.carid.data.bo.AccessTokenBo;
import com.ldblock.carid.data.vo.AccessTokenResponse;
import com.ldblock.carid.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access-token")
public class AccessTokenController {

    @Autowired
    AccessTokenService accessTokenService;

    @PostMapping
    public AccessTokenResponse getAccessToken(AccessTokenBo accessTokenBo) {
        return accessTokenService.getAccessToken(accessTokenBo);
    }
}
