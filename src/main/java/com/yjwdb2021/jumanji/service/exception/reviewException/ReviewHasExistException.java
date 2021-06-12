package com.yjwdb2021.jumanji.service.exception.reviewException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class ReviewHasExistException extends BasicException {

    public ReviewHasExistException(){
        super("error-1002", "이미 등록된 리뷰 번호입니다.");
    }
    public ReviewHasExistException(String code, String message) {
        super(code, message);
    }
}
