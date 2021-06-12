package com.yjwdb2021.jumanji.service.exception.orderMenuOptionException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class OrderMenuOptionHasExistException extends BasicException {
    public OrderMenuOptionHasExistException(){
        super("error-611", "추가된 주문의 메뉴 옵션입니다.");
    }

    /**
     *
     * @param id : 추가 주문 메뉴 옵션의 이름
     */
    public OrderMenuOptionHasExistException(String id){
        super("error-611", "추가된 주문의 메뉴 옵션입니다. 요청한 추가 옵션 : " + id);
    }
    public OrderMenuOptionHasExistException(String code, String message) {
        super(code, message);
    }
}