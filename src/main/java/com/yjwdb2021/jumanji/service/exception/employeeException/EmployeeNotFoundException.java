package com.yjwdb2021.jumanji.service.exception.employeeException;

import com.yjwdb2021.jumanji.service.exception.BasicException;

public class EmployeeNotFoundException extends BasicException {
    public EmployeeNotFoundException(){
        super("error-901", "해당 직원을 찾을 수 없습니다.");
    }

    public EmployeeNotFoundException(String id){
        super("error-901", "해당 직원을 찾을 수 없습니다. id : " + id);
    }

    public EmployeeNotFoundException(String code, String message) {
        super(code, message);
    }


}