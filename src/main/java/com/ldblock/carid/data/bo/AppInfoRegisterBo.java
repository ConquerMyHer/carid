package com.ldblock.carid.data.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AppInfoRegisterBo {

    @NotBlank(message = "appName不能为空")
    private String appName;

    @NotBlank(message = "appIcon不能为空")
    private String appIcon;

    @NotBlank(message = "appDesc不能为空")
    private String appDesc;
}
