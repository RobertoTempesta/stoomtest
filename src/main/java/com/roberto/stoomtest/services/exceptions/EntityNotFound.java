package com.roberto.stoomtest.services.exceptions;

public class EntityNotFound extends RuntimeException {
    
    public EntityNotFound(String msg) {
        super(msg);
    }
}
