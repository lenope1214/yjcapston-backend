package com.yjwdb2021.jumanji.service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ApiErrorResponse{
    private String code;
    private String message;
    private String sql;
//    private String status;
//    private String

    public ApiErrorResponse(BasicException e){
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ApiErrorResponse(String message){
//        super();
        this.message = message;
    }

    public ApiErrorResponse(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ApiErrorResponse(String code, String message, String sql) {
        super();
        this.code = code;
        this.message = message;
        this.sql = sql;
    }
//    public ApiErrorResponse(String code) {
//        switch (code) {
//            case "0000":
//                this.message = "권한이 없습니다.";
//                break;
//            case "0001":
//                this.message = "id로 유저를 찾을 수 없습니다.";
//                break;
//            case "0002":
//                this.message = "이미 있는 아이디입니다.";
//            default:
//                this.message = "알수없는 오류 발생";
//        }
//        this.code = "error-" + code;
//    }

//    public ApiErrorResponse(String error, String message, String status) {
//        super();
//        this.error = error;
//        this.message = message;
//        this.status = status;
//    }
}
