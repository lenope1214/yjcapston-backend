package com.yjwdb2021.jumanji.service.exception.orderException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class OrderNotMineException extends BasicException {
    public OrderNotMineException(){
        super("error-504", "내 주문이 아닙니다.");
    }
    public OrderNotMineException(String orderId){
        super("error-504", "내 주문이 아닙니다." + orderId);
    }
    public OrderNotMineException(String code, String message) {
        super(code, message);
    }
}