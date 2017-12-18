package com.api.platform.web;

/**
 * 功能描述：更新或者删除时用于前端的信息提示
 * @author freedom.xie
 * @version 版本：0.5
 */
public class InfoBean {
	/**成功的状态标示位*/
	public static final String SUCCESS = "1";
	/**失败的状态标示位*/
	public static final String FAILURE = "2";
	/**警告的状态标示位*/
	public static final String WARN = "3";	
	/**代码值*/
	private String code = SUCCESS;
	/**返回消息内容*/
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

