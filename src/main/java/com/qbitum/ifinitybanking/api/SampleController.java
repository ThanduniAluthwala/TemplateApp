package com.qbitum.ifinitybanking.api;

import com.qbitum.ifinitybanking.annotations.OpaPermissionFlux;
import com.qbitum.ifinitybanking.annotations.OpaPermissionMono;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class SampleController {

    @GetMapping("/getString")
    @OpaPermissionMono
    public Mono<String> getString(
        @Parameter(
            name = "Authorization",
            description = "Bearer Authorization token",
            required = true,
            in = ParameterIn.HEADER
        ) @RequestHeader(
            value = "Authorization",
            required = true,
            defaultValue = "Bearer token"
        ) String authorization
    ) {
        return Mono.just("Hello dev");
    }

    @GetMapping("/getStringFlux")
    @OpaPermissionFlux
    public Flux<String> getStringFlux(
        @Parameter(
            name = "Authorization",
            description = "Bearer Authorization token",
            required = true,
            in = ParameterIn.HEADER
        ) @RequestHeader(
            value = "Authorization",
            required = true,
            defaultValue = "Bearer token"
        ) String authorization
    ) {
        return Flux.just("Hello", "World", "from", "Flux");
    }
}
