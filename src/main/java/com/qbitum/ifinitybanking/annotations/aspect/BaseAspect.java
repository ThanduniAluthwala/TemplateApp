package com.qbitum.ifinitybanking.annotations.aspect;

import com.qbitum.ifinitybanking.exception.ServerErrorException;
import com.qbitum.ifinitybanking.exception.enums.ServerError;
import com.qbitum.ifinitybanking.utility.SourcePath;
import java.time.Duration;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This aspect has base aspects that are helpful for and can be used by other aspects
 * Solution for issues faced when using manny aspects for a single method (single ProceedingJoinPoint)
 */
@RequiredArgsConstructor
@Slf4j
public abstract class BaseAspect {

    /***
     * helper for logging execution time after Mono method execution
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    protected Mono<?> logTimeMono(final ProceedingJoinPoint joinPoint)
        throws Throwable {
        LocalTime startTime = LocalTime.now();

        Object result = joinPoint.proceed();

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        //  Can use logMethodExecutionTime.value() to get annotation values

        if (result instanceof Mono) {
            return ((Mono<?>) result).map(
                    signalType -> {
                        LocalTime completeTime = LocalTime.now();
                        Duration completeDuration = Duration.between(
                            startTime,
                            completeTime
                        );
                        long completeTimeDiffSeconds = completeDuration.getSeconds();
                        long completeTimeDiffMilliSec =
                            completeDuration.toMillis() -
                            completeTimeDiffSeconds *
                            1000;
                        try {
                            return signalType;
                        } catch (Throwable e) {
                            log.debug(
                                "Error encountered during execution for method: " +
                                "{}.{} : Duration to return : [{} seconds:{} milliseconds]",
                                className,
                                methodName,
                                completeTimeDiffSeconds,
                                completeTimeDiffMilliSec
                            );
                            return Mono.error(
                                new ServerErrorException(
                                    ServerError.ERROR_UNDEFINED,
                                    SourcePath.findCurrentPath()
                                )
                            );
                        }
                    }
                )
                .doOnSuccess(
                    mono -> {
                        LocalTime completeTime = LocalTime.now();
                        Duration completeDuration = Duration.between(
                            startTime,
                            completeTime
                        );
                        long completeTimeDiffSeconds = completeDuration.getSeconds();
                        long completeTimeDiffMilliSec =
                            completeDuration.toMillis() -
                            completeTimeDiffSeconds *
                            1000;
                        getOnCompleteDebugLog(
                            className,
                            methodName,
                            completeTimeDiffSeconds,
                            completeTimeDiffMilliSec
                        );
                    }
                )
                .doOnError(
                    error -> {
                        LocalTime completeTime = LocalTime.now();
                        Duration completeDuration = Duration.between(
                            startTime,
                            completeTime
                        );
                        long completeTimeDiffSeconds = completeDuration.getSeconds();
                        long completeTimeDiffMilliSec =
                            completeDuration.toMillis() -
                            completeTimeDiffSeconds *
                            1000;
                        log.debug("Other Error data received on error");
                        getOnErrorErrorLog(
                            className,
                            methodName,
                            completeTimeDiffSeconds,
                            completeTimeDiffMilliSec
                        );
                        throw new ServerErrorException(
                            ServerError.ERROR_UNDEFINED,
                            SourcePath.findCurrentPath()
                        );
                    }
                );
        } else {
            getTypeErrorDebugLog(className, methodName);
            return Mono.error(
                new ServerErrorException(
                    ServerError.LOGGING_ANNOTATION_ERROR,
                    SourcePath.findCurrentPath()
                )
            );
        }
    }

    /***
     * helper for logging execution time after Flux method execution
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    protected Flux<?> logTimeFlux(final ProceedingJoinPoint joinPoint)
        throws Throwable {
        LocalTime startTime = LocalTime.now();

        Object result = joinPoint.proceed();

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        //  logMethodExecutionTime.value()

        if (result instanceof Flux) {
            return ((Flux<?>) result).map(
                    signalType -> {
                        LocalTime completeTime = LocalTime.now();
                        Duration completeDuration = Duration.between(
                            startTime,
                            completeTime
                        );
                        long completeTimeDiffSeconds = completeDuration.getSeconds();
                        long completeTimeDiffMilliSec =
                            completeDuration.toMillis() -
                            completeTimeDiffSeconds *
                            1000;

                        try {
                            return signalType;
                        } catch (Throwable e) {
                            getOnErrorErrorLog(
                                className,
                                methodName,
                                completeTimeDiffSeconds,
                                completeTimeDiffMilliSec
                            );
                            return Flux.error(
                                new ServerErrorException(
                                    ServerError.ERROR_UNDEFINED,
                                    SourcePath.findCurrentPath()
                                )
                            );
                        }
                    }
                )
                .doOnComplete(
                    () -> {
                        LocalTime completeTime = LocalTime.now();
                        Duration completeDuration = Duration.between(
                            startTime,
                            completeTime
                        );
                        long completeTimeDiffSeconds = completeDuration.getSeconds();
                        long completeTimeDiffMilliSec =
                            completeDuration.toMillis() -
                            completeTimeDiffSeconds *
                            1000;
                        getOnCompleteDebugLog(
                            className,
                            methodName,
                            completeTimeDiffSeconds,
                            completeTimeDiffMilliSec
                        );
                    }
                )
                .doOnError(
                    error -> {
                        LocalTime completeTime = LocalTime.now();
                        Duration completeDuration = Duration.between(
                            startTime,
                            completeTime
                        );
                        long completeTimeDiffSeconds = completeDuration.getSeconds();
                        long completeTimeDiffMilliSec =
                            completeDuration.toMillis() -
                            completeTimeDiffSeconds *
                            1000;
                        getOnErrorErrorLog(
                            className,
                            methodName,
                            completeTimeDiffSeconds,
                            completeTimeDiffMilliSec
                        );
                    }
                );
        } else {
            getTypeErrorDebugLog(className, methodName);
            return Flux.error(
                new ServerErrorException(
                    ServerError.ERROR_UNDEFINED,
                    SourcePath.findCurrentPath()
                )
            );
        }
    }

    static void getTypeErrorDebugLog(String className, String methodName) {
        log.error(
            "In valid annotation for method : {}.{}",
            className,
            methodName
        );
    }

    static void getOnCompleteDebugLog(
        String className,
        String methodName,
        long completeTimeDiffSeconds,
        long completeTimeDiffMilliSec
    ) {
        log.debug(
            "Execution Duration for method: {}.{} : " +
            "Duration to return : [{} seconds:{} milliseconds]",
            className,
            methodName,
            completeTimeDiffSeconds,
            completeTimeDiffMilliSec
        );
    }

    static void getOnErrorErrorLog(
        String className,
        String methodName,
        long completeTimeDiffSeconds,
        long completeTimeDiffMilliSec
    ) {
        log.error(
            "Error encountered during execution for method: " +
            "{}.{} : Duration to return : [{} seconds:{} milliseconds]",
            className,
            methodName,
            completeTimeDiffSeconds,
            completeTimeDiffMilliSec
        );
    }
}
