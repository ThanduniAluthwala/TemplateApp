package com.qbitum.template.annotations.aspect;

import com.qbitum.template.annotations.LogFluxMethodExecutionTime;
import com.qbitum.template.annotations.LogMethodExecutionTime;
import com.qbitum.template.annotations.LogMethodParams;
import com.qbitum.template.annotations.LogMonoMethodExecutionTime;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Aspect
@Order(3)
public class LogExecutionAspect extends BaseAspect {
    private LocalTime startTime;

    /***
     * Aspect for logging the execution time for method returning Mono<...>, annotated with @LogMonoExecutionTime
     *
     * @param joinPoint represents a point during method execution.
     * @param logMonoMethodExecutionTime name of the custom annotation.
     * @return returns the proceeding value of joint point to the actual SUBSCRIBER of the method.
     * @throws Throwable in case of annotation being used incorrectly for a method not returning a Mono.
     */
    @Around("@annotation(logMonoMethodExecutionTime)")
    public Mono<?> logTimeMono(
        final ProceedingJoinPoint joinPoint,
        LogMonoMethodExecutionTime logMonoMethodExecutionTime
    )
        throws Throwable {
        return logTimeMono(joinPoint);
    }

    /***
     * Aspect for logging the execution time for method returning Flux<...>, annotated with @LogFluxExecutionTime
     *
     * @param joinPoint represents a point during method execution.
     * @param logFluxExecutionTime name of the custom annotation.
     * @return returns the proceeding value of joint point to the actual SUBSCRIBER of the method.
     * @throws Throwable in case of annotation being used incorrectly for a method not returning a Flux.
     */
    @Around("@annotation(logFluxExecutionTime)")
    public Flux<?> lopTimeFlux(
        final ProceedingJoinPoint joinPoint,
        LogFluxMethodExecutionTime logFluxExecutionTime
    )
        throws Throwable {
        return logTimeFlux(joinPoint);
        //        LocalTime startTime = LocalTime.now();
        //
        //        Object result = joinPoint.proceed();
        //
        //        String methodName = joinPoint.getSignature().getName();
        //        String className = joinPoint.getTarget().getClass().getSimpleName();
        //
        //        if (result instanceof Flux) {
        //            return ((Flux<?>) result).map(
        //                    signalType -> {
        //                        LocalTime completeTime = LocalTime.now();
        //                        Duration completeDuration = Duration.between(
        //                            startTime,
        //                            completeTime
        //                        );
        //                        long completeTimeDiffSeconds = completeDuration.getSeconds();
        //                        long completeTimeDiffMilliSec =
        //                            completeDuration.toMillis() -
        //                            completeTimeDiffSeconds *
        //                            1000;
        //
        //                        try {
        //                            return signalType;
        //                        } catch (Throwable e) {
        //                            getOnErrorErrorLog(className,methodName,completeTimeDiffSeconds,completeTimeDiffMilliSec);
        //                            return Flux.error(
        //                                new ServerErrorException(
        //                                    ServerError.ERROR_UNDEFINED,
        //                                    SourcePath.findCurrentPath()
        //                                )
        //                            );
        //                        }
        //                    }
        //                )
        //                .doOnComplete(
        //                    () -> {
        //                        LocalTime completeTime = LocalTime.now();
        //                        Duration completeDuration = Duration.between(
        //                            startTime,
        //                            completeTime
        //                        );
        //                        long completeTimeDiffSeconds = completeDuration.getSeconds();
        //                        long completeTimeDiffMilliSec =
        //                            completeDuration.toMillis() -
        //                            completeTimeDiffSeconds *
        //                            1000;
        //                        getOnCompleteDebugLog(className, methodName, completeTimeDiffSeconds, completeTimeDiffMilliSec);
        //                    }
        //                )
        //                .doOnError(
        //                    error -> {
        //                        LocalTime completeTime = LocalTime.now();
        //                        Duration completeDuration = Duration.between(
        //                            startTime,
        //                            completeTime
        //                        );
        //                        long completeTimeDiffSeconds = completeDuration.getSeconds();
        //                        long completeTimeDiffMilliSec =
        //                            completeDuration.toMillis() -
        //                            completeTimeDiffSeconds *
        //                            1000;
        //                        getOnErrorErrorLog(className,methodName,completeTimeDiffSeconds,completeTimeDiffMilliSec);
        //                    }
        //                );
        //        } else {
        //            getTypeErrorDebugLog(className,methodName);
        //            return Flux.error(
        //                new ServerErrorException(
        //                    ServerError.ERROR_UNDEFINED,
        //                    SourcePath.findCurrentPath()
        //                )
        //            );
        //        }
    }

    /***
     * Aspect for logging the in put parameters of a method when it is being called
     *
     * @param joinPoint represents a point during method execution.
     * @param logMethodParams name of the custom annotation.
     */
    @Before("@annotation(logMethodParams)")
    public void logParams(
        final JoinPoint joinPoint,
        LogMethodParams logMethodParams
    ) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String parameters = Arrays
            .stream(args)
            .map(Object::toString)
            .collect(Collectors.joining());
        startTime = LocalTime.now();
        log.debug(
            "Entering method: {}.{} with parameters: {}",
            className,
            methodName,
            parameters
        );
    }

    @Before("@annotation(logMethodExecutionTime)")
    public void setMethodStartTime(
        LogMethodExecutionTime logMethodExecutionTime
    ) {
        startTime = LocalTime.now();
    }

    /***
     * Aspect to get the execution time of a non-reactive method (not returning mono or flux)
     *
     * @param joinPoint represents a point during method execution.
     * @param logMethodExecutionTime name of the annotation
     * @param result returned object from the method
     */
    @AfterReturning(
        pointcut = "@annotation(logMethodExecutionTime)",
        returning = "result"
    )
    public void logReturn(
        JoinPoint joinPoint,
        LogMethodExecutionTime logMethodExecutionTime,
        Object result
    ) {
        startTime = LocalTime.now();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        LocalTime returnTime = LocalTime.now();
        Duration returnDuration = Duration.between(startTime, returnTime);
        long returnTimeDiffSeconds = returnDuration.getSeconds();
        long returnTimeMilliSec =
            returnDuration.toMillis() - returnTimeDiffSeconds * 1000;

        if (
            !(result instanceof Mono) &&
            !(result instanceof Flux) &&
            result != null
        ) {
            log.debug(
                "Returning from method: {}.{} : Value : {} : " +
                "Duration to return : [{} seconds:{} milliseconds]",
                className,
                methodName,
                result,
                returnTimeDiffSeconds,
                returnTimeMilliSec
            );
        } else {
            getTypeErrorDebugLog(className, methodName);
        }
    }
}
