package com.ldblock.carid.constant;

public class ErrorCode {
	
	/**
	 * showdoc
	 * @catalog 接口文档/全局错误码
	 * @title 全局错误码
	 * @remark 1 成功
	 */
	public static final int OK = 100;
	
	//业务错误
	public static final int NORMAL_ERROR = 101;
	public static final int USER_NOFOUND_ERROR = 105;
	public static final int PASSWORD_ERROR = 106;
	public static final int USER_STATE_ERROR = 107;
	public static final int ARG_ERROR = 106;
	
	//身份错误
	public static final int UNLOGIN = 201;
	public static final int UNAUTH = 202;
	public static final int SESSION_TIMEOUT_ERROR = 204;
	
	public static final int DOMAIN_ERROR = 401;//无权操作
	
	//系统错误
	public static final int LOCK_ERROR = 901; //锁异常
	public static final int DATA_ERROR = 902; //内部数据异常
	public static final int RECOMMIT_ERROR = 908;//重复提交错误
	public static final int SYSTEM_ERROR = 999;

	

	
	
}
