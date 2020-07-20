package com.assessment.customerPortal.controller;

import com.assessment.customerPortal.exception.ErrorDetails;
import com.assessment.customerPortal.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * This class is used for global handling of the exceptions in HTTP request
 *
 */
@ControllerAdvice
public class AbstractRestHandler extends ResponseEntityExceptionHandler  {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Handler for the exception related to resource not found
     * @param ex
     * @param request
     * @return ResponseEntity<RestErrorInfo>
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public
    final ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.info("ResourceNotFoundException handler:" + ex.getMessage());
        ErrorDetails errorInfo = new ErrorDetails(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<ErrorDetails>(errorInfo,HttpStatus.NOT_FOUND);
    }

}