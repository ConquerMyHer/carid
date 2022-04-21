package com.ldblock.carid.controller;

import com.ldblock.carid.data.bo.AppInfoRegisterBo;
import com.ldblock.carid.data.bo.AppInfoUpdateBo;
import com.ldblock.carid.data.po.AppInfo;
import com.ldblock.carid.service.AppInfoService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app-info")
public class AppInfoController {

    @Autowired
    AppInfoService appInfoService;

    @GetMapping
    public AppInfo getAppInfo(String appId) {
        return appInfoService.getAppInfo(appId);
    }

    @PostMapping
    public AppInfo addAppInfo(AppInfoUpdateBo appInfoUpdateBo) {
        return appInfoService.updateAppInfo(appInfoUpdateBo);
    }

    @PostMapping("/register")
    public AppInfo registerAppInfo(AppInfoRegisterBo appInfoRegisterBo) {
        return appInfoService.registerAppInfo(appInfoRegisterBo);
    }

}
