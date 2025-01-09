package com.qbitum.template.server.api;

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
 * A delegate to be called by the {@link V2ApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-09T15:39:05.889641620+05:30[Asia/Colombo]")
public interface V2ApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /v2/sampleTemplate/getData : Sample Get Api using openapi generator
     * Results will be different by product or based on roles of the current logged in user
     *
     * @param authorization Bearer Token for Authorization (required)
     * @param xClientId client ID (required)
     * @param id3  (required)
     * @return list of available components (status code 200)
     *         or Internal server error (status code 500)
     * @see V2Api#getDataV2
     */
    default Mono<ResponseEntity<SampleResponse>> getDataV2(String authorization,
        String xClientId,
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

}
