package com.yjwdb2021.jumanji.service.exception.tableException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class TableAlreadUsingException extends BasicException {
    public TableAlreadUsingException() {
        super("error-5003", "테이블이 사용중입니다.");
    }
}
