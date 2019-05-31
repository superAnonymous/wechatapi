package org.tang.wechat.api.exception;

public class AccessTokenException extends Exception {
	private static final long serialVersionUID = 1L;

	public AccessTokenException(String message) {
		super(message);
	}
}
