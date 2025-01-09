package com.qbitum.template.utility;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class SourcePath {

    public static void logPath() {
        StackTraceElement[] stackTraceElements = Thread
            .currentThread()
            .getStackTrace();

        if (stackTraceElements.length > 2) {
            StackTraceElement callingMethod = stackTraceElements[2];
            String className = callingMethod.getClassName();
            String methodName = callingMethod.getMethodName();
            log.debug("Path: {}.{}", className, methodName);
        } else {
            log.debug("Path not found");
        }
    }

    public static String findCurrentPath() {
        StackTraceElement[] stackTraceElements = Thread
            .currentThread()
            .getStackTrace();

        if (stackTraceElements.length > 2) {
            StackTraceElement callingMethod = stackTraceElements[2];
            String className = callingMethod.getClassName();
            String methodName = callingMethod.getMethodName();
            log.debug("Path: {}.{}", className, methodName);
            return className + "." + methodName;
        } else {
            log.debug("Path Not found");
            return "Path Not Found";
        }
    }

    public static void logThrowableStackTrace(Throwable cause) {
        log.debug("DelegateResponseException received Throwable Error");
        log.error("INTERNAL SERVER ERROR : {}", (Object) cause.getStackTrace());
        if (cause.getCause() != null) log.error(
            "INTERNAL SERVER ERROR : {}",
            (Object) cause
        );
    }
}
