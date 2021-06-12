package com.yjwdb2021.jumanji.service.exception.reviewException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class ReviewIsNotYoursException extends BasicException {

    public ReviewIsNotYoursException(){
        super("error-1004", "요청 유저의 리뷰가 아닙니다.");
    }
    public ReviewIsNotYoursException(String code, String message) {
        super(code, message);
    }
}
