package com.yjwdb2021.jumanji.service.exception.shopException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class ShopMissMatchException extends BasicException {
    public ShopMissMatchException() {
        super("error-203", "잘못된 식당으로 요청이 왔습니다.");
    }
    public ShopMissMatchException(String code, String message) {
        super(code, message);
    }
}
