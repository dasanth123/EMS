package com.example.demo.exceptionHandler;

import com.example.demo.enums.RestApiStatusCodes;
import com.example.demo.utils.ErrorCodes;
import com.example.demo.utils.ErrorDetail;
import com.example.demo.utils.ResponseWrapper;
import com.example.demo.utils.ValidationMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import tools.jackson.databind.exc.InvalidFormatException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private ErrorCodes errorCodes;

    //Request body validation failed (@Valid, @NotNull, @Size, @Email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<List<ErrorDetail>>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorDetail(new Date(),
                        err.getField() + ": " +err.getDefaultMessage(),
                        errorCodes.getMethodArgumentNotValid()))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ResponseWrapper<>(
                RestApiStatusCodes.VALIDATION_FAILED.getCode(),
                RestApiStatusCodes.VALIDATION_FAILED.getMessage(),
                details
        ));

    }
    //Path variable / query param validation failed
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseWrapper<?>> handleConstraintViolation(ConstraintViolationException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                e.getMessage(),
                errorCodes.getConstraintViolation());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.VALIDATION_FAILED.getCode(),
                RestApiStatusCodes.VALIDATION_FAILED.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //URL parameter type mismatch (string instead of int)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseWrapper<?>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                ValidationMessage.TYPE_MISMATCH,
                errorCodes.getTypeMismatch());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.INVALID_PAYLOAD.getCode(),
                RestApiStatusCodes.INVALID_PAYLOAD.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //Invalid data format (email, date, phone, number)
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ResponseWrapper<?>> handleInvalidFormat(InvalidFormatException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                e.getMessage(),
                errorCodes.getInvalidFormat());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.INVALID_PAYLOAD.getCode(),
                RestApiStatusCodes.INVALID_PAYLOAD.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Required query parameter missing
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseWrapper<?>> handleMissingRequestParam(MissingServletRequestParameterException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                ValidationMessage.MISSING_REQUEST_PARAMETER,
                errorCodes.getMissingServletRequestParameter());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.REQUIRED_FIELD_MISSING.getCode(),
                RestApiStatusCodes.REQUIRED_FIELD_MISSING.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //Required header missing (e.g., Authorization)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ResponseWrapper<?>> handleMissingHeader(MissingRequestHeaderException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                ValidationMessage.MISSING_REQUEST_HEADER,
                errorCodes.getMissingRequestHeader());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.REQUIRED_FIELD_MISSING.getCode(),
                RestApiStatusCodes.REQUIRED_FIELD_MISSING.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //Entire payload missing/Some mandatory fields missing
    @ExceptionHandler(RequiredDataMissingException.class)
    public ResponseEntity<ResponseWrapper<?>> handleRequiredDataMissing(RequiredDataMissingException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                e.getMessage(),
                errorCodes.getRequiredDataMissing());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.INVALID_PAYLOAD.getCode(),
                RestApiStatusCodes.INVALID_PAYLOAD.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //Wrong HTTP method (POST used instead of GET)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseWrapper<?>> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                e.getMessage(),
                errorCodes.getHttpRequestMethodNotSupported());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.METHOD_NOT_ALLOWED.getCode(),
                RestApiStatusCodes.METHOD_NOT_ALLOWED.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    //Malformed JSON / unreadable request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseWrapper<?>> handleUnreadable(HttpMessageNotReadableException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                ValidationMessage.UNSUPPORTED_MEDIA_TYPE,
                errorCodes.getHttpMessageNotReadable());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.UNSUPPORTED_MEDIA_TYPE.getCode(),
                RestApiStatusCodes.UNSUPPORTED_MEDIA_TYPE.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }

    //User doesn’t have permission (authorization failure)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseWrapper<?>> handleAccessDenied(AccessDeniedException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                ValidationMessage.ACCESS_DENIED,
                errorCodes.getAccessDenied());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.FORBIDDEN.getCode(),
                RestApiStatusCodes.FORBIDDEN.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }


    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ResponseWrapper<?>> handleAlreadyExist(AlreadyExistException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                e.getMessage(),
                errorCodes.getAlreadyExist());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.ALREADY_EXISTS.getCode(),
                RestApiStatusCodes.ALREADY_EXISTS.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Resource not found using id
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper<?>> handleNotFound(ResourceNotFoundException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                e.getMessage(),
                errorCodes.getResourceNotFound());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.NOT_FOUND.getCode(),
                RestApiStatusCodes.NOT_FOUND.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Invalid argument passed manually
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper<?>> handleIllegalArgument(IllegalArgumentException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                e.getMessage(),
                errorCodes.getIllegalArgument());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.INVALID_STATE.getCode(),
                RestApiStatusCodes.INVALID_STATE.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //Business logic rule failed
    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<ResponseWrapper<?>> handleBusinessRule(BusinessRuleViolationException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(),
                e.getMessage(),
                errorCodes.getBusinessRuleViolation());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> response = new ResponseWrapper<>(
                RestApiStatusCodes.CONFLICT.getCode(),
                RestApiStatusCodes.CONFLICT.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }






    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseWrapper<?>> handleNoHandlerFound (NoHandlerFoundException e){
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(), ValidationMessage.WRONG_API_CALL, errorCodes.getNoHandlerFound());
        errorDetails.add(errorDetail);

        ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(
                RestApiStatusCodes.NO_HANDLER_FOUND.getCode(),
                RestApiStatusCodes.NO_HANDLER_FOUND.getMessage(),
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseWrapper);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseWrapper<?>> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                ValidationMessage.MEDIA_TYPE_NOT_SUPPORTED,
                errorCodes.getMediaTypeNotSupported()
        );
        errorDetails.add(errorDetail);

        ResponseWrapper<?> responseWrapper = new ResponseWrapper<>(
                RestApiStatusCodes.MEDIA_TYPE_NOT_SUPPORTED.getCode(),
                RestApiStatusCodes.MEDIA_TYPE_NOT_SUPPORTED.getMessage(),
                errorDetails
        );

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(responseWrapper);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseWrapper<?>> unauthorized(UnauthorizedException e){
        List<ErrorDetail> errorDetails=new ArrayList<>();
        ErrorDetail errorDetail=new ErrorDetail(new Date(),
                e.getMessage(),
                errorCodes.getUnauthorized());
        errorDetails.add(errorDetail);
        ResponseWrapper<?> response=new ResponseWrapper<>(
                RestApiStatusCodes.UNAUTHORIZED.getCode(),
                RestApiStatusCodes.UNAUTHORIZED.getMessage(),
                errorDetails
        );
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }



}
