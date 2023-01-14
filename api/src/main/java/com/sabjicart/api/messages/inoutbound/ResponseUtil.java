
package com.sabjicart.api.messages.inoutbound;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ResponseUtil
{
    public static void error (Response response)
    {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
    }

    public static void success (Response response)
    {
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());
    }

    public static void notImplemented (Response response)
    {
        response.setStatus(HttpStatus.NOT_IMPLEMENTED);
        response.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
    }

    public static void error (Response response, ErrorMessage message)
    {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.addErrorMessage(message);
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
    }

    public static void notImplemented (Response response, ErrorMessage message)
    {
        response.setStatus(HttpStatus.NOT_IMPLEMENTED);
        response.addErrorMessage(message);
        response.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
    }

    public static void error (Response response, String message)
    {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        List<ErrorMessage> errorMessageList = new ArrayList();
        addErrorMessageToList(message, errorMessageList);
        response.addErrorMessages(errorMessageList);
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
    }

    public static void notImplemented (Response response, String message)
    {
        response.setStatus(HttpStatus.NOT_IMPLEMENTED);
        List<ErrorMessage> errorMessageList = new ArrayList();
        addErrorMessageToList(message, errorMessageList);
        response.addErrorMessages(errorMessageList);
        response.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
    }

    private static void addErrorMessageToList (
        String message, List<ErrorMessage> errorMessageList)
    {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorType(ErrorType.Error);
        errorMessage.setMessage(message);
        errorMessageList.add(errorMessage);
    }
}
