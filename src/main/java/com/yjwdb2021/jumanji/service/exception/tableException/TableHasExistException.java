package com.yjwdb2021.jumanji.service.exception.tableException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class TableHasExistException extends BasicException {
    public TableHasExistException() {
        super("error-5002", "The table id is exist");
    }
    public TableHasExistException(String code, String message) {
        super(code, message);
    }
}
