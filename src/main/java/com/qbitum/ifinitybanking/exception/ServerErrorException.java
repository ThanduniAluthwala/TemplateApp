package com.qbitum.ifinitybanking.exception;

import com.qbitum.ifinitybanking.exception.enums.ServerError;
import com.qbitum.ifinitybanking.server.model.ServerErrorResponse;
import com.qbitum.ifinitybanking.utility.SourcePath;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class ServerErrorException extends RuntimeException {
    private final ServerErrorResponse errorObject;
    private final ServerError serverErrorType;
    private final String sourcePath;
    private final ServerErrorException serverErrorException;
    private final String serverErrorDescription;

    /***
     * Constructor for Server error with Error type and origin path
     *
     * @param type error type from ServerError Enum
     * @param sourcePath origin of exception, use SourcePath util to get the path
     */
    public ServerErrorException(ServerError type, String sourcePath) {
        super(type.getMessage());
        this.serverErrorType = type;
        this.sourcePath = sourcePath;
        this.serverErrorDescription = null;
        this.serverErrorException = null;
        this.errorObject = null;
    }

    /***
     * Constructor for Server error with error Object, mostly used for exception passed via WebClient requests
     *
     * @param error an object passed in the exception class
     */
    public ServerErrorException(ServerErrorResponse error) {
        this.errorObject = error;
        this.serverErrorType = null;
        this.sourcePath = null;
        this.serverErrorException = null;
        this.serverErrorDescription = null;
    }

    /***
     * Constructor for server error
     *
     * @param type error type from ServerError Enum
     * @param message a custom description for the exception, in addition to error message in type enum
     * @param cause
     * @param sourcePath origin of exception, use SourcePath util to get the path
     */
    public ServerErrorException(
        ServerError type,
        String message,
        String sourcePath,
        Throwable cause
    ) {
        super(message, cause);
        if (cause instanceof ServerErrorException) {
            this.serverErrorException = (ServerErrorException) cause;
        } else {
            this.serverErrorException = null;
        }

        SourcePath.logThrowableStackTrace(cause);
        this.serverErrorType = type;
        this.sourcePath = sourcePath;
        this.serverErrorDescription = message;
        this.errorObject = null;
    }

    /***
     * Constructor for server error with an undefined throwable passed as the error
     *
     * @param cause A throwable passed in to ServerErrorException, preferably an exception of the same class
     */
    public ServerErrorException(Throwable cause) {
        super(cause);
        if (cause instanceof ServerErrorException) {
            this.serverErrorException = (ServerErrorException) cause;
            this.serverErrorType = serverErrorException.getServerErrorType();
            this.sourcePath = serverErrorException.getSourcePath();
        } else {
            this.serverErrorException = null;
            this.serverErrorType = null;
            this.sourcePath = null;
        }
        SourcePath.logThrowableStackTrace(cause);
        this.errorObject = null;
        this.serverErrorDescription = null;
    }

    /***
     * Constructor for custom server error with description, try to use this constructor as much as possible
     *
     * @param type error type from ServerError Enum
     * @param sourcePath origin of exception, use SourcePath util to get the path
     * @param message a custom description for the exception, in addition to error message in type enum
     */
    public ServerErrorException(
        ServerError type,
        String sourcePath,
        String message
    ) {
        super(message);
        this.serverErrorException = null;
        this.serverErrorType = type;
        this.sourcePath = sourcePath;
        this.serverErrorDescription = message;
        this.errorObject = null;
    }
}
