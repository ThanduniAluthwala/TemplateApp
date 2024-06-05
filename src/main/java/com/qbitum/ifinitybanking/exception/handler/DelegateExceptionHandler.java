package com.qbitum.ifinitybanking.exception.handler;

import com.qbitum.ifinitybanking.exception.DelegateResponseException;
import com.qbitum.ifinitybanking.exception.enums.ServerError;
import com.qbitum.ifinitybanking.server.model.ServerErrorResponse;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class DelegateExceptionHandler {

    /***
     * Handler for throwing delegate level exception as ServerErrorResponse Objects
     *
     * @param e The throwable exception
     * @return returns a Http Response Entity
     */
    @ExceptionHandler(value = { DelegateResponseException.class })
    public ResponseEntity<ServerErrorResponse> handleApiRequestException(
        DelegateResponseException e
    ) {
        // payload with exception details

        ServerErrorResponse serverErrorResponse = new ServerErrorResponse();
        serverErrorResponse.status("ERROR");

        if (e.getServerErrorException() != null) {
            if (e.getServerErrorException().getErrorObject() != null) {
                serverErrorResponse.setCode(
                    e.getServerErrorException().getErrorObject().getCode()
                );
                serverErrorResponse.setMessage(
                    e.getServerErrorException().getErrorObject().getMessage()
                );
                serverErrorResponse.setErrorPath(
                    e.getServerErrorException().getErrorObject().getErrorPath()
                );
                serverErrorResponse.setDescription(
                    e
                        .getServerErrorException()
                        .getErrorObject()
                        .getDescription()
                );
            } else {
                serverErrorResponse.setCode(
                    e.getServerErrorException().getServerErrorType().getCode()
                );
                serverErrorResponse.setMessage(
                    e
                        .getServerErrorException()
                        .getServerErrorType()
                        .getMessage()
                );
                serverErrorResponse.setErrorPath(
                    e.getServerErrorException().getSourcePath()
                );
                serverErrorResponse.setDescription(e.getDelegateDescription());
            }
        } else {
            if (e.getErrorResponseObject() != null) {
                log.debug("Passing pre-def error object");
                serverErrorResponse = e.getErrorResponseObject();
            } else {
                if (e.getErrorType() == null || e.getPath() == null) {
                    serverErrorResponse.setCode(
                        ServerError.ERROR_UNDEFINED.getCode()
                    );
                    serverErrorResponse.setMessage(
                        ServerError.ERROR_UNDEFINED.getMessage()
                    );
                    serverErrorResponse.setErrorPath(
                        Arrays.toString(e.getStackTrace())
                    );
                    serverErrorResponse.setDescription(
                        e.getDelegateDescription()
                    );
                } else {
                    serverErrorResponse.setErrorPath(e.getPath());
                    serverErrorResponse.setCode(e.getErrorType().getCode());
                    serverErrorResponse.setMessage(
                        e.getErrorType().getMessage()
                    );
                    serverErrorResponse.setDescription(
                        e.getDelegateDescription()
                    );
                }
            }
        }

        log.error("Delegate Level Error : {} : ", serverErrorResponse);

        // return response entity
        return new ResponseEntity<>(
            serverErrorResponse,
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
