package com.qbitum.template.client.api;

import com.qbitum.template.client.ApiClient;

import java.math.BigDecimal;
import com.qbitum.template.client.model.WeatherInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-01-09T15:41:22.497372960+05:30[Asia/Colombo]")
public class WeatherInfoApi {
    private ApiClient apiClient;

    public WeatherInfoApi() {
        this(new ApiClient());
    }

    @Autowired
    public WeatherInfoApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get weather information using user location
     * 
     * <p><b>200</b> - Weather information retrieved successfully
     * @param latitude The latitude parameter
     * @param longitude The longitude parameter
     * @return WeatherInfo
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec weatherInfoRequestCreation(BigDecimal latitude, BigDecimal longitude) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'latitude' is set
        if (latitude == null) {
            throw new WebClientResponseException("Missing the required parameter 'latitude' when calling weatherInfo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'longitude' is set
        if (longitude == null) {
            throw new WebClientResponseException("Missing the required parameter 'longitude' when calling weatherInfo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "latitude", latitude));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "longitude", longitude));
        
        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<WeatherInfo> localVarReturnType = new ParameterizedTypeReference<WeatherInfo>() {};
        return apiClient.invokeAPI("/weather-info", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get weather information using user location
     * 
     * <p><b>200</b> - Weather information retrieved successfully
     * @param latitude The latitude parameter
     * @param longitude The longitude parameter
     * @return WeatherInfo
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<WeatherInfo> weatherInfo(BigDecimal latitude, BigDecimal longitude) throws WebClientResponseException {
        ParameterizedTypeReference<WeatherInfo> localVarReturnType = new ParameterizedTypeReference<WeatherInfo>() {};
        return weatherInfoRequestCreation(latitude, longitude).bodyToMono(localVarReturnType);
    }

    /**
     * Get weather information using user location
     * 
     * <p><b>200</b> - Weather information retrieved successfully
     * @param latitude The latitude parameter
     * @param longitude The longitude parameter
     * @return ResponseEntity&lt;WeatherInfo&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<WeatherInfo>> weatherInfoWithHttpInfo(BigDecimal latitude, BigDecimal longitude) throws WebClientResponseException {
        ParameterizedTypeReference<WeatherInfo> localVarReturnType = new ParameterizedTypeReference<WeatherInfo>() {};
        return weatherInfoRequestCreation(latitude, longitude).toEntity(localVarReturnType);
    }

    /**
     * Get weather information using user location
     * 
     * <p><b>200</b> - Weather information retrieved successfully
     * @param latitude The latitude parameter
     * @param longitude The longitude parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec weatherInfoWithResponseSpec(BigDecimal latitude, BigDecimal longitude) throws WebClientResponseException {
        return weatherInfoRequestCreation(latitude, longitude);
    }
}
