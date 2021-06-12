package com.yjwdb2021.jumanji.service.exception.shopException;

import com.yjwdb2021.jumanji.service.exception.BasicException;
import lombok.Getter;
import lombok.Setter;

public class ShopHasExistException extends BasicException {

    public ShopHasExistException(){
        super("error-202", "이미 등록된 사업자 번호입니다.");
    }
    public ShopHasExistException(String code, String message) {
        super(code, message);
    }
}
