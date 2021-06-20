package com.yjwdb2021.jumanji.service.exception.userException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class UserHasExistException extends BasicException {
    public UserHasExistException() {
        super("error-103", "존재하는 아이디 입니다.");
    }

    public UserHasExistException(String id) {
        super("error-103", "존재하는 아이디 입니다. - id : " + id);
    }

    public UserHasExistException(String code, String message) {
        super(code, message);
    }


}