package com.qbitum.template.exception;

import com.qbitum.template.exception.enums.ServerError;
import com.qbitum.template.server.model.ServerErrorResponse;
import com.qbitum.template.utility.SourcePath;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class DelegateResponseException extends RuntimeException {
    private final ServerErrorException serverErrorException;
    private final ServerError errorType;
    private final String path;
    private final String delegateDescription;
    private final ServerErrorResponse errorResponseObject;

    /***
     * Constructor for Delegate level exceptions with a passed throwable with custom description
     *
     * @param message a custom description for the exception, in addition to error message in cause
     * @param cause a throwable exception, preferably of ServerErrorException class
     */
    public DelegateResponseException(String message, Throwable cause) {
        super(cause);
        SourcePath.logThrowableStackTrace(cause);
        this.delegateDescription = message;
        this.errorType = null;
        this.path = null;
        if (cause instanceof ServerErrorException) {
            this.serverErrorException = (ServerErrorException) cause;
        } else {
            this.serverErrorException = null;
        }
        this.errorResponseObject = null;
    }

    /***
     * Constructor for Delegate level exceptions with a passed throwable
     *
     * @param cause a throwable exception, preferably of ServerErrorException class
     */
    public DelegateResponseException(Throwable cause) {
        super(cause);
        SourcePath.logThrowableStackTrace(cause);
        if (cause instanceof ServerErrorException) {
            this.serverErrorException = (ServerErrorException) cause;
            log.debug(
                "Description : {}",
                ((ServerErrorException) cause).getServerErrorDescription()
            );
            this.delegateDescription =
                ((ServerErrorException) cause).getServerErrorDescription();
        } else {
            this.serverErrorException = null;
            this.delegateDescription = null;
        }
        this.errorType = null;
        this.path = null;
        this.errorResponseObject = null;
    }

    /***
     * Use this Constructor to pass an ErrorResponse object, in case of an unidentified error is thrown
     * Then the ErrorResponse object will be passed as the response instead of default
     * Recommended for use
     * @param cause receive Throwable error cause
     * @param error Pre-def ServerErrorResponse object
     */
    public DelegateResponseException(
        Throwable cause,
        ServerErrorResponse error
    ) {
        super(cause);
        SourcePath.logThrowableStackTrace(cause);
        if (cause instanceof ServerErrorException) {
            this.serverErrorException = (ServerErrorException) cause;
            log.debug(
                "Description : {}",
                ((ServerErrorException) cause).getServerErrorDescription()
            );
            this.delegateDescription =
                ((ServerErrorException) cause).getServerErrorDescription();
            this.errorResponseObject = null;
        } else {
            this.serverErrorException = null;
            this.delegateDescription = null;
            this.errorResponseObject = error;
        }
        this.errorType = null;
        this.path = null;
    }

    /***
     *  Constructor for Delegate level exceptions without a throwable passed
     *
     * @param message a custom description for the exception, in addition to error message in cause
     * @param type error type from ServerError Enum
     * @param path origin of exception, use SourcePath util to get the path
     */
    public DelegateResponseException(
        String message,
        ServerError type,
        String path
    ) {
        super(message);
        this.serverErrorException = null;
        this.errorType = type;
        this.path = path;
        this.delegateDescription = message;
        this.errorResponseObject = null;
    }
}
