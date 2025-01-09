# WeatherInfoApi

All URIs are relative to *http://localhost:4010*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**weatherInfo**](WeatherInfoApi.md#weatherInfo) | **GET** /weather-info | Get weather information using user location |



## weatherInfo

> WeatherInfo weatherInfo(latitude, longitude)

Get weather information using user location

### Example

```java
// Import classes:
import com.qbitum.template.client.ApiClient;
import com.qbitum.template.client.ApiException;
import com.qbitum.template.client.Configuration;
import com.qbitum.template.client.models.*;
import com.qbitum.template.client.api.WeatherInfoApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:4010");

        WeatherInfoApi apiInstance = new WeatherInfoApi(defaultClient);
        BigDecimal latitude = new BigDecimal(78); // BigDecimal | 
        BigDecimal longitude = new BigDecimal(78); // BigDecimal | 
        try {
            WeatherInfo result = apiInstance.weatherInfo(latitude, longitude);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling WeatherInfoApi#weatherInfo");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **latitude** | **BigDecimal**|  | |
| **longitude** | **BigDecimal**|  | |

### Return type

[**WeatherInfo**](WeatherInfo.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Weather information retrieved successfully |  -  |

