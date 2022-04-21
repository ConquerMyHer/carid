package com.ldblock.carid.data.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AppInfoUpdateBo {
    @NotBlank(message = "appId不能为空")
    private String appId;

    private String appName;

    private String appDesc;

    private String appIcon;
}
