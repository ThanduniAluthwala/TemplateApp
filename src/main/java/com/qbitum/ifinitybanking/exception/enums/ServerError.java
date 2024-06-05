package com.qbitum.ifinitybanking.exception.enums;

import lombok.Getter;

/**
 * This Enumeration class is for handling internal server Exceptions.
 * These errors are standard and will be shared between all teams of a project.
 * If a developer has a need to update or add to these Enums, it is his/her responsibility to communicate the changes with the affecting teams.
 */
@Getter
public enum ServerError {
    UNAUTHORIZED_API(510, "API Denied for User Token"),
    INACCESSIBLE_METHOD(511, "Method Access Denied for User Token"),
    METHOD_NOT_FOUND(512, "Method Not Found"),
    ERROR_UNDEFINED(513, "Unidentified Error Detected : CRITICAL"),
    DATA_LAYER_NOT_FOUND_ERROR(514, "Requested data is not found on database"),
    DATA_LAYER_INVALID_INSERT_ERROR(515, "Invalid data inserted to database"),
    LOGGING_ANNOTATION_ERROR(516, "Log annotation error"),
    UNDEFINED_WEBCLIENT_ERROR(
        517,
        "Unidentified Error received from WebClient request : CRITICAL"
    ),
    ANNOTATION_USAGE_ERROR(
        516,
        "Incorrect use of custom annotations, Re-evaluate annotated methods"
    );

    private final int code;
    private final String message;

    ServerError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
