package com.ssa.hrmsCustomer.common.exception;

import org.springframework.stereotype.Component;

public class UserAlreadyExist extends RuntimeException{

    public UserAlreadyExist(String message){
        super(message);
    }
}
