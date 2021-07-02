package com.roberto.stoomtest.controllers.exceptions;

import java.io.Serializable;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StandardError implements Serializable {
    
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
