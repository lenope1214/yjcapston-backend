package com.yjwdb2021.jumanji.service.exception.orderException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class OrderNotPaidException extends BasicException {
    public OrderNotPaidException(){
        super("error-503", "주문이 완료돼야 합니다.");
    }
    public OrderNotPaidException(String id){
        super("error-503", "주문이 완료돼야 합니다. : " + id);
    }
    public OrderNotPaidException(String code, String message) {
        super(code, message);
    }
}
