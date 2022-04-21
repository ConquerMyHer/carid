package com.ldblock.carid.data.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
	
    public static final String SUCCESS_MSG = "成功";
    
	private int code;

    private Object data;

    private String message;
    
    public Response(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.message = msg;
    }

	public static Response Error(int code, String msg) {
		return new Response(code, null, msg);
	}

}
