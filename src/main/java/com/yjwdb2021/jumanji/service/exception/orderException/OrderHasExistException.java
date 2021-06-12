package com.yjwdb2021.jumanji.service.exception.orderException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class OrderHasExistException extends BasicException {
    public OrderHasExistException(){
        super("error-502", "해당 주문이 이미 있습니다.");
    }
    public OrderHasExistException(String code, String message) {
        super(code, message);
    }
}
