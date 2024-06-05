package com.qbitum.ifinitybanking.delegate.v1;

import com.qbitum.ifinitybanking.annotations.LogMethodParams;
import com.qbitum.ifinitybanking.annotations.LogMonoMethodExecutionTime;
import com.qbitum.ifinitybanking.exception.DelegateResponseException;
import com.qbitum.ifinitybanking.exception.enums.ServerError;
import com.qbitum.ifinitybanking.server.api.V1ApiDelegate;
import com.qbitum.ifinitybanking.server.model.SampleData;
import com.qbitum.ifinitybanking.server.model.SampleRequest;
import com.qbitum.ifinitybanking.server.model.SampleResponse;
import com.qbitum.ifinitybanking.service.impl.SampleServiceImpl;
import com.qbitum.ifinitybanking.utility.ReadJwt;
import com.qbitum.ifinitybanking.utility.SourcePath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class TemplateSampleDelegateV1 implements V1ApiDelegate {
    @Autowired
    private SampleServiceImpl sampleService;

    /***
     * Sample delegate implementation to demonstrate use of OpenApi generator with different params and use of annotations
     *
     * @param authorization Bearer Token for Authorization (required)
     * @param xClientId client ID (required)
     * @param id1  (required)
     * @param id2  (required)
     * @param id3  (required)
     * @param exchange
     * @return
     */
    @Override
    @LogMonoMethodExecutionTime
    @LogMethodParams
    public Mono<ResponseEntity<SampleResponse>> sampleTemplateGetApi(
        String authorization,
        String xClientId,
        String id1,
        String id2,
        String id3,
        ServerWebExchange exchange
    ) {
        List<SampleData> sampleDataList = Arrays.asList(
            new SampleData(id1, id2, id3),
            new SampleData(id2, id2, id3)
        );

        // Use the parseJWT method in the ReadJwt utility "ReadJwt.parseJWT()" class to decode a jwt and get user data
        SampleResponse response = new SampleResponse(
            ReadJwt.parseJWT(authorization).get("sub").toString(),
            "This is a Sample Response with response status : OK",
            sampleDataList
        );

        log.debug("Response body : {}", response);
        return Mono
            .just(ResponseEntity.ok(response))
            .doOnError(
                error -> {
                    throw new DelegateResponseException(error);
                }
            );
    }

    /***
     * Sample delegate implementation to demonstrate use of OpenApi generator post api
     *
     * @param authorization Bearer Token for Authorization (required)
     * @param xClientId client ID (required)
     * @param sampleRequest  (required)
     * @param exchange
     * @return
     */
    @Override
    @LogMonoMethodExecutionTime
    @LogMethodParams
    public Mono<ResponseEntity<SampleResponse>> sampleTemplatePostApi(
        String authorization,
        String xClientId,
        Mono<SampleRequest> sampleRequest,
        ServerWebExchange exchange
    ) {
        return sampleRequest
            .flatMap(
                sampleRequestValue -> {
                    log.info("Request body object : {}", sampleRequestValue);
                    List<SampleData> sampleDataList = new ArrayList<>();
                    for (SampleData listObject : sampleRequestValue.getIdList()) {
                        sampleDataList.add(
                            new SampleData(
                                listObject.getId1(),
                                listObject.getId2(),
                                listObject.getId3()
                            )
                        );
                    }

                    // Use the parseJWT method in the ReadJwt utility class to decode a jwt and get user data
                    SampleResponse response = new SampleResponse(
                        ReadJwt.parseJWT(authorization).get("sub").toString(),
                        "This is a Sample Response with data list received",
                        sampleDataList
                    );
                    response.setCode(201);
                    log.debug("Response body : {}", response);
                    //  Try sending 500 as 'number' in the request body to view an example of exception handling
                    if (sampleRequestValue.getNumber() == 500) {
                        throw new DelegateResponseException(
                            "Demo Delegate exception",
                            ServerError.ERROR_UNDEFINED,
                            SourcePath.findCurrentPath()
                        );
                    }
                    return Mono.just(ResponseEntity.status(201).body(response));
                }
            )
            .doOnError(
                error -> {
                    throw new DelegateResponseException(error);
                }
            );
    }
}
