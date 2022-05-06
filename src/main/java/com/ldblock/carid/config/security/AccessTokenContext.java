package com.ldblock.carid.config.security;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = false)
public class AccessTokenContext {

    private String openId;

    /*
    **  TODO: 其余字段
    */
    private String whateverString;
}
