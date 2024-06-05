package com.qbitum.ifinitybanking.annotations.aspect;

import com.qbitum.ifinitybanking.annotations.LogFluxMethodExecutionTime;
import com.qbitum.ifinitybanking.annotations.LogMonoMethodExecutionTime;
import com.qbitum.ifinitybanking.annotations.OpaPermissionFlux;
import com.qbitum.ifinitybanking.annotations.OpaPermissionMono;
import com.qbitum.ifinitybanking.exception.DelegateResponseException;
import com.qbitum.ifinitybanking.exception.enums.ServerError;
import com.qbitum.ifinitybanking.utility.SourcePath;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Aspect class to provide custom annotation usage rules
 */

@Configuration
@Slf4j
@Aspect
@Order(1)
public class AnnotationCombinationAspect {

    /***
     * Forbid combined usage of @LogMonoMethodExecutionTime and @OpaPermissionMono on methods
     */
    @Before(
        "@annotation(logMonoMethodExecutionTime) && @annotation(opaPermissionMono)"
    )
    public void checkMonoLogAuthCombination(
        JoinPoint joinPoint,
        LogMonoMethodExecutionTime logMonoMethodExecutionTime,
        OpaPermissionMono opaPermissionMono
    ) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        throw new DelegateResponseException(
            "A method Cannot be annotated with @LogMonoMethodExecutionTime and @OpaPermissionMono, Use @OpaPermissionMono instead",
            ServerError.ANNOTATION_USAGE_ERROR,
            "Exception : " +
            SourcePath.findCurrentPath() +
            " : Origin : " +
            className +
            " -> " +
            methodName
        );
    }

    /***
     * Forbid combined usage of @LogMonoMethodExecutionTime and @LogFluxMethodExecutionTime on methods
     */
    @Before(
        "@annotation(logMonoMethodExecutionTime) && @annotation(logFluxMethodExecutionTime)"
    )
    public void checkMonoFluxLogCombination(
        JoinPoint joinPoint,
        LogMonoMethodExecutionTime logMonoMethodExecutionTime,
        LogFluxMethodExecutionTime logFluxMethodExecutionTime
    ) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        throw new DelegateResponseException(
            "A method Cannot be annotated with @LogMonoMethodExecutionTime and @LogFluxMethodExecutionTime.",
            ServerError.ANNOTATION_USAGE_ERROR,
            "Exception : " +
            SourcePath.findCurrentPath() +
            ": Origin : " +
            className +
            " -> " +
            methodName
        );
    }

    /***
     * Forbid combined usage of @OpaPermissionFlux and @LogFluxMethodExecutionTime on methods
     */
    @Before(
        "@annotation(opaPermissionFlux) && @annotation(logFluxMethodExecutionTime)"
    )
    public void checkFluxOpaLogCombination(
        JoinPoint joinPoint,
        OpaPermissionFlux opaPermissionFlux,
        LogFluxMethodExecutionTime logFluxMethodExecutionTime
    ) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        throw new DelegateResponseException(
            "A method Cannot be annotated with @OpaPermissionFlux and @LogFluxMethodExecutionTime, use @OpaPermissionFlux instead",
            ServerError.ANNOTATION_USAGE_ERROR,
            "Exception : " +
            SourcePath.findCurrentPath() +
            ": Origin : " +
            className +
            " -> " +
            methodName
        );
    }

    /***
     * Forbid combined usage of @OpaPermissionMono and @LogFluxMethodExecutionTime on methods
     */
    @Before(
        "@annotation(opaPermissionMono) && @annotation(logFluxMethodExecutionTime)"
    )
    public void checkMonoOpaFluxLogCombination(
        JoinPoint joinPoint,
        OpaPermissionMono opaPermissionMono,
        LogFluxMethodExecutionTime logFluxMethodExecutionTime
    ) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        throw new DelegateResponseException(
            "A method Cannot be annotated with @OpaPermissionMono and @LogFluxMethodExecutionTime.",
            ServerError.ANNOTATION_USAGE_ERROR,
            "Exception : " +
            SourcePath.findCurrentPath() +
            ": Origin : " +
            className +
            " -> " +
            methodName
        );
    }

    /***
     * Forbid combined usage of @OpaPermissionFlux and @LogMonoMethodExecutionTime on methods
     */
    @Before(
        "@annotation(opaPermissionFlux) && @annotation(logMonoMethodExecutionTime)"
    )
    public void checkFluxOpaMonoLogCombination(
        JoinPoint joinPoint,
        OpaPermissionFlux opaPermissionFlux,
        LogMonoMethodExecutionTime logMonoMethodExecutionTime
    ) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        throw new DelegateResponseException(
            "A method Cannot be annotated with @OpaPermissionFlux and @LogMonoMethodExecutionTime.",
            ServerError.ANNOTATION_USAGE_ERROR,
            "Exception : " +
            SourcePath.findCurrentPath() +
            ": Origin : " +
            className +
            " -> " +
            methodName
        );
    }
}
