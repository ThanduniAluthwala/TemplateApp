package com.qbitum.ifinitybanking.annotations.aspect;

import com.qbitum.ifinitybanking.Model.PermissionModel;
import com.qbitum.ifinitybanking.annotations.OpaPermissionFlux;
import com.qbitum.ifinitybanking.annotations.OpaPermissionMono;
import com.qbitum.ifinitybanking.configurations.OpaConfig;
import com.qbitum.ifinitybanking.exception.DelegateResponseException;
import com.qbitum.ifinitybanking.exception.ServerErrorException;
import com.qbitum.ifinitybanking.exception.enums.ServerError;
import com.qbitum.ifinitybanking.server.model.ServerErrorResponse;
import com.qbitum.ifinitybanking.utility.SourcePath;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Method Permission aspect
 * Has the aspect priority of Order - 2 after AnnotationCombinationAspect
 * Gets permissions from OPA Server, Therefore cannot be used without it
 */

@Slf4j
@Aspect
@Component
@Order(2)
public class GetOpaAuthAspect extends BaseAspect {
    @Autowired
    private WebClient.Builder webClientBuilderDefault;

    private final OpaConfig opaConfig;

    public GetOpaAuthAspect(OpaConfig opaConfig) {
        this.opaConfig = opaConfig;
    }

    /***
     * Aspect for OpaPermissionMono annotation to get permission details of the token to access the annotated method returning a Mono
     * @param joinPoint
     * @param opaPermissionMono
     * @return
     * @throws Throwable
     */
    @Around("@annotation(opaPermissionMono)")
    public Mono<?> getAuthMono(
        final ProceedingJoinPoint joinPoint,
        OpaPermissionMono opaPermissionMono
    )
        throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        String token = null;

        for (Object arg : args) {
            if (arg instanceof String && ((String) arg).startsWith("Bearer")) {
                token = ((String) arg).substring(7);
                break;
            }
        }

        if (token == null) return Mono.error(
            new DelegateResponseException(
                "No Bearer Token",
                ServerError.UNAUTHORIZED_API,
                SourcePath.findCurrentPath()
            )
        );

        log.debug("Bearer Token Identified: {}", token);
        String finalUrl = opaConfig.getOpaUrl() + methodName + "/allow";
        log.debug("Opa call Url : {}", finalUrl);

        return webClientBuilderDefault
            .build()
            .post()
            .uri(finalUrl)
            .bodyValue(Map.of("input", Map.of("token", token)))
            .exchangeToMono(
                clientResponse -> {
                    if (
                        clientResponse.statusCode() ==
                        HttpStatusCode.valueOf(200)
                    ) {
                        return clientResponse.bodyToMono(PermissionModel.class);
                    } else {
                        log.debug("OPA response Error");
                        // Make sure to mention the response status code
                        String description =
                            "Error received of Http status : " +
                            clientResponse.statusCode().toString();
                        return Mono.error(
                            new ServerErrorException(
                                ServerError.UNDEFINED_WEBCLIENT_ERROR,
                                finalUrl,
                                description
                            )
                        );
                    }
                }
            )
            .doOnNext(
                str -> {
                    log.debug("Opa Permission Status received : {}", str);
                    log.debug("Proceeding join point");
                }
            )
            .doOnError(
                error -> {
                    ServerErrorResponse errorObject = new ServerErrorResponse();
                    errorObject.setCode(
                        ServerError.UNDEFINED_WEBCLIENT_ERROR.getCode()
                    );
                    errorObject.setMessage(
                        ServerError.UNDEFINED_WEBCLIENT_ERROR.getMessage()
                    );
                    errorObject.setErrorPath(finalUrl);
                    errorObject.setStatus("ERROR");
                    errorObject.setDescription("UnCaught Webclient Error");
                    throw new DelegateResponseException(error, errorObject);
                }
            )
            .flatMap(
                str -> {
                    if (
                        str != null &&
                        str.getResult() != null &&
                        str instanceof PermissionModel
                    ) {
                        if (!str.getResult().equals("true")) {
                            return Mono.error(
                                new ServerErrorException(
                                    ServerError.UNAUTHORIZED_API,
                                    SourcePath.findCurrentPath(),
                                    "Permission Denied from OPA server"
                                )
                            );
                        }
                        try {
                            log.debug("Mono object received");
                            return logTimeMono(joinPoint);
                        } catch (Throwable e) {
                            log.error(
                                "Undefined Error : {} : {}",
                                e.getMessage(),
                                e.getStackTrace()
                            );
                            return Mono.error(
                                new ServerErrorException(
                                    ServerError.ERROR_UNDEFINED,
                                    SourcePath.findCurrentPath()
                                )
                            );
                        }
                    } else {
                        String description = "Undefined Error Detected";
                        return Mono.error(
                            new ServerErrorException(
                                ServerError.UNDEFINED_WEBCLIENT_ERROR,
                                finalUrl,
                                description
                            )
                        );
                    }
                }
            );
    }

    /***
     * Aspect for OpaPermissionMono annotation to get permission details of the token to access the annotated method returning a Mono
     * @param joinPoint
     * @param opaPermissionFlux
     * @return
     * @throws Throwable
     */
    @Around("@annotation(opaPermissionFlux)")
    public Flux<?> getAuthFlux(
        final ProceedingJoinPoint joinPoint,
        OpaPermissionFlux opaPermissionFlux
    )
        throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        String token = null;

        for (Object arg : args) {
            if (arg instanceof String && ((String) arg).startsWith("Bearer")) {
                token = ((String) arg).substring(7);
                break;
            }
        }

        if (token == null) return Flux.error(
            new DelegateResponseException(
                "No Bearer Token",
                ServerError.UNAUTHORIZED_API,
                SourcePath.findCurrentPath()
            )
        );

        log.debug("Bearer Token Identified: {}", token);
        String finalUrl = opaConfig.getOpaUrl() + methodName + "/allow";
        log.debug("Opa call Url : {}", finalUrl);

        return webClientBuilderDefault
            .build()
            .post()
            .uri(finalUrl)
            .bodyValue(Map.of("input", Map.of("token", token)))
            .exchangeToFlux(
                clientResponse -> {
                    if (
                        clientResponse.statusCode() ==
                        HttpStatusCode.valueOf(200)
                    ) {
                        return clientResponse.bodyToFlux(PermissionModel.class);
                    } else {
                        // Make sure to mention the response status code
                        String description =
                            "Error received of Http status : " +
                            clientResponse.statusCode().toString();
                        return Flux.error(
                            new ServerErrorException(
                                ServerError.UNDEFINED_WEBCLIENT_ERROR,
                                finalUrl,
                                description
                            )
                        );
                    }
                }
            )
            .doOnNext(
                str -> {
                    log.debug("Opa Permission received received : {}", str);
                    log.debug("Proceeding joint point");
                }
            )
            .doOnError(
                error -> {
                    throw new DelegateResponseException(error);
                }
            )
            .flatMap(
                str -> {
                    if (
                        str != null &&
                        str.getResult() != null &&
                        str instanceof PermissionModel
                    ) {
                        if (!str.getResult().equals("true")) {
                            return Flux.error(
                                new ServerErrorException(
                                    ServerError.UNAUTHORIZED_API,
                                    SourcePath.findCurrentPath(),
                                    "Permission Denied from OPA server"
                                )
                            );
                        }
                        try {
                            log.debug("Mono permission object received");
                            return logTimeFlux(joinPoint);
                        } catch (Throwable e) {
                            return Flux.error(
                                new ServerErrorException(
                                    ServerError.ERROR_UNDEFINED,
                                    SourcePath.findCurrentPath()
                                )
                            );
                        }
                    } else {
                        String description = "Undefined Error Detected";
                        return Flux.error(
                            new ServerErrorException(
                                ServerError.UNDEFINED_WEBCLIENT_ERROR,
                                finalUrl,
                                description
                            )
                        );
                    }
                }
            );
    }
}
