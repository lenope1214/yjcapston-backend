package com.yjwdb2021.jumanji.service.exception.employeeException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class EmployeeAlreadyStartException extends BasicException {
    public EmployeeAlreadyStartException() {
        super("error-911", "직원이 이미 출근하였습니다.");
    }

    public EmployeeAlreadyStartException(String id) {
        super("error-911", "직원이 이미 출근하였습니다. id : " + id);
    }

    public EmployeeAlreadyStartException(String code, String message) {
        super(code, message);
    }


}