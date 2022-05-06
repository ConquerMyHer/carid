package com.ldblock.carid.controller;

import com.ldblock.carid.config.security.UserContext;
import com.ldblock.carid.data.bo.AppAuthCodeBo;
import com.ldblock.carid.data.vo.AuthCodeResponse;
import com.ldblock.carid.service.AppAuthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app-auth-code")
public class AppAuthCodeController {

    @Autowired
    AppAuthCodeService appAuthCodeService;

//    @PostMapping
//    public AuthCodeResponse requestAppAuthCode(AppAuthCodeBo appAuthCodeBo) {
//        return appAuthCodeService.getAuthCode(appAuthCodeBo);
//    }

    @PostMapping
    public AuthCodeResponse requestAppAuthCode(AppAuthCodeBo appAuthCodeBo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return appAuthCodeService.getAuthCode(appAuthCodeBo, userContext.getId().toString());
//        return "success";
    }
}
