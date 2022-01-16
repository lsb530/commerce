package com.boki.commerce.api.user.exception;

import com.boki.commerce.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super("This user is Not Found");
    }
}