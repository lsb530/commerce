package com.boki.commerce.common.exception;

public class BadRequestException extends RuntimeException {

	public BadRequestException(String message) {
		super("잘못된 요청 : " + message);
	}
}