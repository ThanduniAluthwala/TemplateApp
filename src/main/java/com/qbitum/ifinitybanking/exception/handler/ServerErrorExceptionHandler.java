package com.qbitum.ifinitybanking.exception.handler;

import com.qbitum.ifinitybanking.exception.ServerErrorException;
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
public class ServerErrorExceptionHandler {

    /***
     * Handler for throwing server error exception as ServerErrorResponse Objects
     *
     * @param e The throwable exception
     * @return returns a Http Response Entity
     */
    @ExceptionHandler(value = { ServerErrorException.class })
    public ResponseEntity<ServerErrorResponse> handleServerErrorException(
        ServerErrorException e
    ) {
        // payload with exception details

        ServerErrorResponse serverErrorResponse = new ServerErrorResponse();
        serverErrorResponse.setStatus("ERROR");
        if (e.getErrorObject() == null) {
            if (e.getServerErrorException() != null) {
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
            } else {
                if (
                    e.getServerErrorType() == null || e.getSourcePath() == null
                ) {
                    serverErrorResponse.setCode(
                        ServerError.ERROR_UNDEFINED.getCode()
                    );
                    serverErrorResponse.setMessage(
                        ServerError.ERROR_UNDEFINED.getMessage()
                    );
                    serverErrorResponse.setErrorPath(
                        Arrays.toString(e.getStackTrace())
                    );
                } else {
                    serverErrorResponse.setCode(
                        e.getServerErrorType().getCode()
                    );
                    serverErrorResponse.setMessage(
                        e.getServerErrorType().getMessage()
                    );
                    serverErrorResponse.errorPath(e.getSourcePath());
                }
            }
            serverErrorResponse.setDescription(e.getServerErrorDescription());
        } else {
            serverErrorResponse.setMessage(e.getErrorObject().getMessage());
            serverErrorResponse.setCode(e.getErrorObject().getCode());
            serverErrorResponse.setErrorPath(e.getErrorObject().getErrorPath());
        }

        log.error("Server Level Error : {} : ", serverErrorResponse);
        log.error("INTERNAL SERVER ERROR : {}", (Object) e.getStackTrace());
        if (e.getCause() != null) log.error(
            "INTERNAL SERVER ERROR : {}",
            (Object) e.getCause()
        );
        // return response entity
        return new ResponseEntity<>(
            serverErrorResponse,
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
