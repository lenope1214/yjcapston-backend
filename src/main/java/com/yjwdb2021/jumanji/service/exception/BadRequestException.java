package com.yjwdb2021.jumanji.service.exception;

public class BadRequestException extends BasicException {
    public BadRequestException() {
        this("잘못된 요청입니다.");
    }

    public BadRequestException(String id) {
        super("BAD-REQUEST", id);
    }



}