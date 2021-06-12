package com.yjwdb2021.jumanji.service.exception.reviewException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class ReviewNotFoundException extends BasicException{
    public ReviewNotFoundException() {
        super("error-1001", "없는 리뷰번호 입니다.");
    }
    public ReviewNotFoundException(String code, String message) {
        super(code, message);
    }
}

