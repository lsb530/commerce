package com.boki.commerce.api.item.exception;

import com.boki.commerce.common.exception.NotFoundException;

public class ItemNotFoundException extends NotFoundException {

    public ItemNotFoundException() {
        super("This item is Not Found");
    }
}