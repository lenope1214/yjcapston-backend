package com.yjwdb2021.jumanji.service.exception.orderMenuOptionException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class OrderMenuOptionNotFoundException extends BasicException {
    public OrderMenuOptionNotFoundException() {
        super("error-612", "없는 옵션 그룹 입니다.");
    }
}