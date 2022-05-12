package com.ldblock.carid.data.bo;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
public class CheckVINBo {

    @NotBlank(message = "appId不能为空")
    private String appId;

    @NotBlank(message = "appSecret不能为空")
    private String secret;

    @NotBlank(message = "vin不能为空")
    private String vin;

    @NotBlank(message = "accessToken不能为空")
    private String accessToken;
}
