package com.ldblock.carid.data.bo;

import lombok.Data;
import org.springframework.cache.annotation.Cacheable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Data
public class AppAuthCodeBo {

    @NotBlank(message = "appId不能为空")
    private String appId;

    @NotBlank(message = "appAuthRequest不能为空")
    @Pattern(regexp = "\\[([a-zA-z0-9]+,)*[a-zA-z0-9]+\\]", message = "appAuthRequest格式不正确")
    private String appAuthRequest;

    /**
     * appAuthRequest的解析
     */
    public List<String> parseAuthRequest() {
        String tempAppAuthRequest = appAuthRequest.replace("[", "").replace("]", "");
        return List.of(tempAppAuthRequest.split(","));
    }

    public String generateAuthCode() {
        // 使用uuid生成authCode
        return UUID.randomUUID().toString();
    }


}
