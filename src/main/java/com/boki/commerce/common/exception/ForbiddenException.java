package com.boki.commerce.common.exception;

public class ForbiddenException extends RuntimeException {

	public ForbiddenException(String message) {
		super("금지된 요청 : " + message);
	}
}