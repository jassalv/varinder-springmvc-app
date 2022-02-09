package com.spring.firstthymeleafapp.processor;

import com.spring.firstthymeleafapp.constants.CommonConstants;
import com.spring.firstthymeleafapp.utils.ErrorInfo;
import com.spring.firstthymeleafapp.utils.MessageUtils;
import com.spring.firstthymeleafapp.utils.RequestedResource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler extends ResponseEntityExceptionHandler {
    private static final Logger loggerObj = LoggerFactory.getLogger(ErrorHandler.class);
    @Autowired
    private Environment environment;

    /**
     * This method handles all the cases where a given object fails the simple JSR-303
     * validation as annotated in the class definition using JSR-303 annotations like @NotBlank
     * (non-Javadoc)
     *
     * @see ResponseEntityExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders, HttpStatus, WebRequest)
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        loggerObj.error("{}", MessageUtils.getFullStackDetailsAsString(ex));
        ErrorInfo errorInfo = new ErrorInfo();
        List<ErrorInfo.DetailedMessage> detailedMessages = new ArrayList<>();
        errorInfo.setDetailedMessages(detailedMessages);
        if (ex.getBindingResult() != null && ex.getBindingResult().getAllErrors() != null) {
            ex.getBindingResult().getAllErrors().forEach(objectError -> {
                ErrorInfo.DetailedMessage detailedMessage = new ErrorInfo.DetailedMessage();
                detailedMessage.setMessage(objectError.getDefaultMessage());
                detailedMessage.setObject(objectError.getObjectName());
                if (objectError instanceof FieldError) {
                    detailedMessage.setField(((FieldError) objectError).getField());
                }
                detailedMessages.add(detailedMessage);
            });
        }
        errorInfo.setHttpStatusCode(status.toString());
        errorInfo.setHttpStatusMessage(status.getReasonPhrase());
        checkAndPopulateRequestURL(errorInfo, (RequestedResource) request.getAttribute(CommonConstants.REQUESTED_RESOURCE,
                RequestAttributes.SCOPE_REQUEST));
        return new ResponseEntity(errorInfo, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles all InValid data cases. They can be input data validation failures or busines rules
     * validation failures. And converts them into HTTP_404 Bad_request message highlighting the actual object.field
     * that caused the validation rule failure.
     *
     * @param ex
     * @return ResponseEntity
     */
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Object> handleException(final InvalidDataException ex, WebRequest request) {
        loggerObj.error("{}", MessageUtils.getFullStackDetailsAsString(ex));
        ErrorInfo errorInfo = populateErrorInfo(ex);
        checkAndPopulateRequestURL(errorInfo, (RequestedResource) request.getAttribute(CommonConstants.REQUESTED_RESOURCE,
                RequestAttributes.SCOPE_REQUEST));
        errorInfo.setHttpStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorInfo.setHttpStatusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity(errorInfo, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method is used by called APIs to signal that there's no data available for a given parameter value.
     * An HTTP_204 status code is sent to the calling client.
     *
     * @param ex
     * @return ResponseEntity
     */
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Object> handleException(final NoDataFoundException ex, WebRequest request) {
        loggerObj.error("{}", MessageUtils.getFullStackDetailsAsString(ex));
        ErrorInfo errorInfo = populateErrorInfo(ex);
        checkAndPopulateRequestURL(errorInfo, (RequestedResource) request.getAttribute(CommonConstants.REQUESTED_RESOURCE,
                RequestAttributes.SCOPE_REQUEST));
        errorInfo.setHttpStatusCode(HttpStatus.NO_CONTENT.toString());
        errorInfo.setHttpStatusMessage(HttpStatus.NO_CONTENT.getReasonPhrase());
        //
        return new ResponseEntity(errorInfo, HttpStatus.NO_CONTENT);
    }

    /**
     * This method handles all runtimeExceptions like Host-Not-Reachable or Network-down etc.
     *
     * @param ex
     * @param request
     * @return ResponseEntity
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleException(final Throwable ex, WebRequest request) {
        loggerObj.error("{}", MessageUtils.getFullStackDetailsAsString(ex));
        //
        ErrorInfo errorInfo = new ErrorInfo();
        if (ex instanceof AppRuntimeException) {
            errorInfo = populateErrorInfo((AppRuntimeException) ex);
        }

        if (request instanceof ServletWebRequest) {
            ((ServletWebRequest) request).toString();
            errorInfo.setUrl(((ServletWebRequest) request).toString());
        }

        //
        if (!(ex instanceof AppRuntimeException)) {

            List<ErrorInfo.DetailedMessage> detailedMessages = new ArrayList<>();
            errorInfo.setDetailedMessages(detailedMessages);
            ErrorInfo.DetailedMessage detailedMessage = new ErrorInfo.DetailedMessage();
            checkAndPopulateRequestURL(errorInfo, (RequestedResource) request.getAttribute(CommonConstants.REQUESTED_RESOURCE,
                    RequestAttributes.SCOPE_REQUEST));
            errorInfo.getDetailedMessages().add(detailedMessage);

        }
        errorInfo.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorInfo.setHttpStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        //
//        if(!isEnvProd(environment)){
//            detailedMessage.setMessage(MessageUtils.getFullStackDetailsAsString(ex));
//        }else{
//            detailedMessage.setMessage(ex.getClass()!=null?ex.getClass().toString():null);
//        }
        return new ResponseEntity(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private void checkAndPopulateRequestURL(ErrorInfo errorInfo, RequestedResource requestedResource) {
        if (requestedResource != null && StringUtils.isNotBlank(requestedResource.getRequestUrl())) {
            errorInfo.setUrl(requestedResource.getRequestUrl());
        }
    }

    private ErrorInfo populateErrorInfo(final AppRuntimeException ex) {
        ErrorInfo errorInfo = new ErrorInfo();
        List<ErrorInfo.DetailedMessage> detailedMessages = new ArrayList<>();
        if (ex.getErrorMap() != null) {
            ex.getErrorMap().forEach((key, value) -> {
                ErrorInfo.DetailedMessage detailedMessage = new ErrorInfo.DetailedMessage();
                detailedMessage.setMessage(value);
                detailedMessage.setObject(key);
                detailedMessages.add(detailedMessage);
            });
        }
        errorInfo.setDetailedMessages(detailedMessages);
        return errorInfo;
    }
}
