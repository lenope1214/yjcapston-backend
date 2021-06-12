package com.yjwdb2021.jumanji.service.exception;

import com.yjwdb2021.jumanji.service.exception.orderException.*;
import com.yjwdb2021.jumanji.service.exception.auth.ForbiddenException;
import com.yjwdb2021.jumanji.service.exception.employeeException.EmployeeAlreadyStartException;
import com.yjwdb2021.jumanji.service.exception.employeeException.EmployeeDoesNotStartException;
import com.yjwdb2021.jumanji.service.exception.employeeException.EmployeeHasExistException;
import com.yjwdb2021.jumanji.service.exception.employeeException.EmployeeNotFoundException;
import com.yjwdb2021.jumanji.service.exception.menuException.MenuHasExistException;
import com.yjwdb2021.jumanji.service.exception.optionException.OptionHasExistException;
import com.yjwdb2021.jumanji.service.exception.optionException.OptionNotFoundException;
import com.yjwdb2021.jumanji.service.exception.optionGroupException.OptionGroupHasExistException;
import com.yjwdb2021.jumanji.service.exception.optionGroupException.OptionGroupNotFoundException;
import com.yjwdb2021.jumanji.service.exception.orderMenuException.OrderMenuNotFoundException;
import com.yjwdb2021.jumanji.service.exception.orderMenuOptionException.OrderMenuOptionHasExistException;
import com.yjwdb2021.jumanji.service.exception.orderMenuOptionException.OrderMenuOptionNotFoundException;
import com.yjwdb2021.jumanji.service.exception.reviewException.ReviewHasExistException;
import com.yjwdb2021.jumanji.service.exception.shopException.*;
import com.yjwdb2021.jumanji.service.exception.tableException.TableAlreadUsingException;
import com.yjwdb2021.jumanji.service.exception.userException.DoLoginExistException;
import com.yjwdb2021.jumanji.service.exception.userException.LoginFailedException;
import com.yjwdb2021.jumanji.service.exception.userException.UserHasExistException;
import com.yjwdb2021.jumanji.service.exception.userException.UserNotFoundException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.persistence.NonUniqueResultException;

@ControllerAdvice @RequiredArgsConstructor
public class ApiExceptionHandler{


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> idHandleException(UserNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserHasExistException.class)
    public ResponseEntity<ApiErrorResponse> idHandleException(UserHasExistException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ApiErrorResponse> pwHandleException(LoginFailedException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ShopNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> shopException(ShopNotFoundException ex){
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ShopHasExistException.class)
    public ResponseEntity<ApiErrorResponse> shopException(ShopHasExistException ex){
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderMenuNotFoundException.class)
    public ResponseEntity<?> orderException(OrderMenuNotFoundException ex){
        ApiErrorResponse response=
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MenuHasExistException.class)
    public ResponseEntity<?> menuException(MenuHasExistException ex){
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ShopMissMatchException.class)
    public ResponseEntity<?> menuException(ShopMissMatchException ex){
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoLoginExistException.class)
    public ResponseEntity<?> AuthenticationException(DoLoginExistException ex){
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
//        new ApiErrorResponse("error-0000 : 권한없음", "로그인해주세요");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoShopListException.class)
    public ResponseEntity<?> shopException(NoShopListException ex){
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return  new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> dataDuplicateKeyException(ConstraintViolationException ex){
        ApiErrorResponse response =
                new ApiErrorResponse("ORA-0001", ex.getConstraintName(), ex.getSQL());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<?> jwtException(MalformedJwtException ex){
        ApiErrorResponse response =
                new ApiErrorResponse("token-0001", "토큰이 제대로 넘어오지 않습니다.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<?> missingHeader(MissingRequestHeaderException ex){
        ApiErrorResponse response =
                new ApiErrorResponse("badRequest-0001", "헤더가 제대로 넘어오지 않습니다.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> empNotFoundException(EmployeeNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeHasExistException.class)
    public ResponseEntity<ApiErrorResponse> empHasExistException(EmployeeHasExistException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CanNotBeZero.class)
    public ResponseEntity<ApiErrorResponse> canNotBeZero(CanNotBeZero ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiErrorResponse> forbidden(ForbiddenException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> orderNotFound(OrderNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MyNullPointerException.class)
    public ResponseEntity<ApiErrorResponse> nullPointException(MyNullPointerException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<ApiErrorResponse> myNonUniqueResultException(NonUniqueResultException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("error-value-non-unique", "유니크 제약조건 위반");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReviewHasExistException.class)
    public ResponseEntity<ApiErrorResponse> reviewHasExistException(ReviewHasExistException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OptionNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> optionNotFoundException(OptionNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(OptionHasExistException.class)
    public ResponseEntity<ApiErrorResponse> optionHasExistException(OptionHasExistException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(OptionGroupNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> optionGroupNotFoundException(OptionGroupNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(OptionGroupHasExistException.class)
    public ResponseEntity<ApiErrorResponse> optionGroupHasExistException(OptionGroupHasExistException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OrderMenuOptionNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> orderMenuOptionNotFoundException(OrderMenuOptionNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderMenuOptionHasExistException.class)
    public ResponseEntity<ApiErrorResponse> orderMenuOptionHasExistException(OrderMenuOptionHasExistException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmployeeDoesNotStartException.class)
    public ResponseEntity<ApiErrorResponse> employeeDoesNotStartException(EmployeeDoesNotStartException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmployeeAlreadyStartException.class)
    public ResponseEntity<ApiErrorResponse> employeeAlreadyStartException(EmployeeAlreadyStartException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorResponse> maxUploadSizeExceededException(){
        ApiErrorResponse response =
                new ApiErrorResponse("error-file-size", "파일 사이즈가 너무 큽니다!");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotMineException.class)
    public ResponseEntity<ApiErrorResponse> orderNotMineException(OrderNotMineException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OrderNotPaidException.class)
    public ResponseEntity<ApiErrorResponse> orderNotMineException(OrderNotPaidException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PayPointOverException.class)
    public ResponseEntity<ApiErrorResponse> payPointOverException(PayPointOverException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ShopNotOpenException.class)
    public ResponseEntity<ApiErrorResponse> shopNotOpenException(ShopNotOpenException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderAmountCanNotZeroException.class)
    public ResponseEntity<ApiErrorResponse> orderAmountCanNotZeroException(OrderAmountCanNotZeroException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TableAlreadUsingException.class)
    public ResponseEntity<ApiErrorResponse> tableAlreadUsingException(TableAlreadUsingException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}