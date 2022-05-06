package com.ldblock.carid.data.bo;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
public class AccessTokenBo {

    @NotBlank(message = "appId不能为空")
    private String appId;

    @NotBlank(message = "appSecret不能为空")
    private String secret;

    @NotBlank(message = "code不能为空")
    private String code;

}
