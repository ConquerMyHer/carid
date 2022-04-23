package com.ldblock.carid.data.vo;

import lombok.Getter;

import java.util.List;

@Getter
public class AuthCodeResponse {

    private String authCode;

    private List<String> authorities;

//    public static final String ERROR_MESSAGE = "请求权限码失败：没有合法的权限";

//    public static final String SUCCESS_MESSAGE = "请求权限码成功";

    public AuthCodeResponse(String authCode, List<String> authorities) {
        this.authCode = authCode;
        this.authorities = authorities;
    }

    public AuthCodeResponse() {
        authCode = "";
        authorities = null;
    }


}
