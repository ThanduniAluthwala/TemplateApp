package com.qbitum.template.server.api;

import com.qbitum.template.server.model.SampleRequest;
import com.qbitum.template.server.model.SampleResponse;
import com.qbitum.template.server.model.ServerErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.codec.multipart.Part;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link V1ApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-13T18:17:34.618622380+05:30[Asia/Colombo]")
public interface V1ApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /v1/sampleTemplate/data/{id1},{id2} : Sample Get Api using openapi generator
     * Results will be different by product or based on roles of the current logged in user
     *
     * @param authorization Bearer Token for Authorization (required)
     * @param xClientId client ID (required)
     * @param id1  (required)
     * @param id2  (required)
     * @param id3  (required)
     * @return list of available components (status code 200)
     *         or Internal server error (status code 500)
     * @see V1Api#sampleTemplateGetApi
     */
    default Mono<ResponseEntity<SampleResponse>> sampleTemplateGetApi(String authorization,
        String xClientId,
        String id1,
        String id2,
        String id3,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"code\" : 0, \"data\" : [ { \"id2\" : \"id2\", \"id1\" : \"id1\", \"id3\" : \"id3\" }, { \"id2\" : \"id2\", \"id1\" : \"id1\", \"id3\" : \"id3\" } ], \"message\" : \"message\", \"userId\" : \"userId\" }";
                result = ApiUtil.getExampleResponse(exchange, MediaType.valueOf("application/json"), exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }

    /**
     * POST /v1/sampleTemplate/postData : Sample post Api using openapi generator
     * Results will be different by product or based on roles of the current logged in user
     *
     * @param authorization Bearer Token for Authorization (required)
     * @param xClientId client ID (required)
     * @param sampleRequest  (required)
     * @return list of components has been added (status code 201)
     *         or Internal server error (status code 500)
     * @see V1Api#sampleTemplatePostApi
     */
    default Mono<ResponseEntity<SampleResponse>> sampleTemplatePostApi(String authorization,
        String xClientId,
        Mono<SampleRequest> sampleRequest,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"code\" : 0, \"data\" : [ { \"id2\" : \"id2\", \"id1\" : \"id1\", \"id3\" : \"id3\" }, { \"id2\" : \"id2\", \"id1\" : \"id1\", \"id3\" : \"id3\" } ], \"message\" : \"message\", \"userId\" : \"userId\" }";
                result = ApiUtil.getExampleResponse(exchange, MediaType.valueOf("application/json"), exampleString);
                break;
            }
        }
        return result.then(sampleRequest).then(Mono.empty());

    }

}
