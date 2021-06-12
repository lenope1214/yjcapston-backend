package com.yjwdb2021.jumanji.service.exception.userException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class DoLoginExistException extends BasicException {
    public DoLoginExistException() {
        super("error-login", "You have to login");
    }
}