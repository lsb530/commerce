package com.boki.commerce.api.user.exception;


import com.boki.commerce.common.exception.DuplicatedException;

public class DuplicatedEmailException extends DuplicatedException {

    public DuplicatedEmailException() {
        super("This email is already being used");
    }
}