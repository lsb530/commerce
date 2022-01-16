package com.boki.commerce.common.exception;

public class UnauthorizedException extends RuntimeException {

	public UnauthorizedException(String message) {
		super("Unauthorized Request : " + message);
	}
}