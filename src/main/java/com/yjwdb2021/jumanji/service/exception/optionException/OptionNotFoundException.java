package com.yjwdb2021.jumanji.service.exception.optionException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class OptionNotFoundException extends BasicException {
    public OptionNotFoundException() {
        super("2001", "없는 옵션 입니다.");
    }
    public OptionNotFoundException(String id) {
        super("2001", "없는 옵션 입니다. id : " + id);
    }
    public OptionNotFoundException(String code, String message) {
        super(code, message);
    }
}