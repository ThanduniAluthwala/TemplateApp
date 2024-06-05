package com.qbitum.ifinitybanking.delegate.v2;

import com.qbitum.ifinitybanking.exception.DelegateResponseException;
import com.qbitum.ifinitybanking.server.api.V2ApiDelegate;
import com.qbitum.ifinitybanking.server.model.SampleData;
import com.qbitum.ifinitybanking.server.model.SampleResponse;
import com.qbitum.ifinitybanking.utility.ReadJwt;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class TemplateSampleDelegateV2 implements V2ApiDelegate {

    @Override
    public Mono<ResponseEntity<SampleResponse>> getDataV2(
        String authorization,
        String xClientId,
        String id3,
        ServerWebExchange exchange
    ) {
        List<SampleData> sampleDataList = new ArrayList<>();
        sampleDataList.add(new SampleData("1", "2", id3));

        // Use the parseJWT method in the ReadJwt utility class to decode a jwt and get user data
        SampleResponse response = new SampleResponse(
            ReadJwt.parseJWT(authorization).get("sub").toString(),
            "This is a Sample Response with data list received",
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
}
