package com.ldblock.carid.controller;

import com.ldblock.carid.config.security.OpenContext;
import com.ldblock.carid.config.security.UserContext;
import com.ldblock.carid.data.bo.AccessTokenBo;
import com.ldblock.carid.data.bo.CheckVINBo;
import com.ldblock.carid.data.vo.AccessTokenResponse;
import com.ldblock.carid.data.vo.ServerCheckVINResponse;
import com.ldblock.carid.service.AccessTokenService;
import com.ldblock.carid.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class ServerController {

    @Autowired
    ServerService serverService;

    @PostMapping("/checkVIN")
    public ServerCheckVINResponse checkVIN(CheckVINBo checkVINBo) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        OpenContext openContext = (OpenContext) authentication.getPrincipal();
        return serverService.checkVIN(checkVINBo);
    }
}
