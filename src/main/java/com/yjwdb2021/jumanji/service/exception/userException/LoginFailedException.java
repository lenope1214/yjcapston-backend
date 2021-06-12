package com.yjwdb2021.jumanji.service.exception.userException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class LoginFailedException extends BasicException {
    public LoginFailedException(){
        super("error-102", "login failed");
    }

//    public LoginFailedMatchException(String password) {
//        super("error-102", "login failed : " id);
//    }
}