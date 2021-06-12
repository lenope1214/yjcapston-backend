package com.yjwdb2021.jumanji.service.exception.markException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class MarkNotFoundException extends BasicException {
    public MarkNotFoundException() {
        super("error-1101", "찜등록이 안되어있는 식당 입니다.");
    }
    public MarkNotFoundException(String shopId) {
        super("error-1101", "찜등록이 안되어있는 식당 입니다." + shopId);
    }
    public MarkNotFoundException(String code, String message) {
        super(code, message);
    }
}