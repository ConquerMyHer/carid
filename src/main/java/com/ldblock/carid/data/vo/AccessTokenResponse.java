package com.ldblock.carid.data.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Chang Han
 */
@AllArgsConstructor
@NoArgsConstructor
//@Setter
@Getter
public class AccessTokenResponse {

    private String openId;

    private String accessToken;

    private String refreshToken;
}
