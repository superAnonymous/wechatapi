package org.tang.wechat.api.exception;

public class WechatException extends Exception {
	private static final long serialVersionUID = 1L;

	public WechatException(String errorMessage) {
		super(errorMessage);
	}
}
