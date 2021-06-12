package com.yjwdb2021.jumanji.service.exception.employeeException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class EmployeeHasExistException extends BasicException {
    public EmployeeHasExistException() {
        super("error-902", "해당 번호의 직원이 존재합니다.");
    }

    public EmployeeHasExistException(String id) {
        super("error-902", "해당 번호의 직원이 존재합니다. id : " + id);
    }

    public EmployeeHasExistException(String code, String message) {
        super(code, message);
    }


}