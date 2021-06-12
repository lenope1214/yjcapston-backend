package com.yjwdb2021.jumanji.service.exception.orderException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class OrderAmountCanNotZeroException extends BasicException {
    public OrderAmountCanNotZeroException(){
        super("error-509", "주문금액이 0원이 될 수 없습니다.");
    }
    public OrderAmountCanNotZeroException(String id){
        super("error-not-defined", "주문금액이 0원이 될 수 없습니다. !! 요청시간 : " + id);
    }
    public OrderAmountCanNotZeroException(String code, String message) {
        super(code, message);
    }
}
