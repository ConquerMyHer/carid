package com.ldblock.carid.config.security;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder=false)
public class OpenContext {

	private String openid;

	private String userName;
}
