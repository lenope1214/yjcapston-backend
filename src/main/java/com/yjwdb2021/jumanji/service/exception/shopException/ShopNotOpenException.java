package com.yjwdb2021.jumanji.service.exception.shopException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class ShopNotOpenException extends BasicException {

    public ShopNotOpenException(){
        super("error-205", "식당이 오픈중이 아닙니다.");
    }
    public ShopNotOpenException(String code, String message) {
        super(code, message);
    }
}
