package com.yjwdb2021.jumanji.service.exception.optionGroupException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class OptionGroupNotFoundException extends BasicException {
    public OptionGroupNotFoundException() {
        super("2001", "없는 옵션 그룹 입니다.");
    }
    public OptionGroupNotFoundException(String code, String message) {
        super(code, message);
    }
}