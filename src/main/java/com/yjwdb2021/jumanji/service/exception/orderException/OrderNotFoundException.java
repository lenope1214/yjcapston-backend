package com.yjwdb2021.jumanji.service.exception.orderException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class OrderNotFoundException extends BasicException {
    public OrderNotFoundException(){
        super("error-501", "주문을 찾을 수 없습니다.");
    }
    public OrderNotFoundException(String id){
        super("error-501", "주문을 찾을 수 없습니다. id : " + id);
    }
    public OrderNotFoundException(String code, String message) {
        super(code, message);
    }
}
