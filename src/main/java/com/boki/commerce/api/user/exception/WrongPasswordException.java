package com.boki.commerce.api.user.exception;

import com.boki.commerce.common.exception.BadRequestException;

public class WrongPasswordException extends BadRequestException {

    public WrongPasswordException() {
        super("wrong password");
    }
}