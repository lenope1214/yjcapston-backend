package com.yjwdb2021.jumanji.service.exception.orderMenuException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class OrderMenuNotFoundException extends BasicException {
    public OrderMenuNotFoundException(){
        super("error-601", "없는 주문번호 입니다.");
    }
    public OrderMenuNotFoundException(String code, String message) {
        super(code, message);
    }
}