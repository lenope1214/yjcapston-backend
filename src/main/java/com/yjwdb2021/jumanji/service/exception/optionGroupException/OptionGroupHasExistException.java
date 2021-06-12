package com.yjwdb2021.jumanji.service.exception.optionGroupException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class OptionGroupHasExistException extends BasicException {
    public OptionGroupHasExistException(){
        super("2002", "존재하는 옵션 그룹입니다.");
    }
    public OptionGroupHasExistException(String id){
        super("2002", "존재하는 옵션 그룹입니다. : " + id);
    }
    public OptionGroupHasExistException(String code, String message) {
        super(code, message);
    }
}