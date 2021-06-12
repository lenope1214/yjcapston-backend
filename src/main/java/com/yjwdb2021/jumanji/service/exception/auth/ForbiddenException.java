package com.yjwdb2021.jumanji.service.exception.auth;

import com.yjwdb2021.jumanji.service.exception.BasicException;


// unauthorized = 무단 => 'jwt' 없음
// unauthenticated = 인증되지 않음 => 'jwt'는 있지만 권한 불충분.
public class ForbiddenException extends BasicException {
    public ForbiddenException(){
        super("error-0000", "권한없음");
    }
    public ForbiddenException(String code, String message) {
        super(code, message);
    }
}
