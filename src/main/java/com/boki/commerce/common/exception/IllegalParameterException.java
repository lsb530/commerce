package com.boki.commerce.common.exception;

public class IllegalParameterException extends BadRequestException {

	private static final String MESSAGE = "Invalid Parameter";

	public IllegalParameterException() {
		super(MESSAGE);
	}
}