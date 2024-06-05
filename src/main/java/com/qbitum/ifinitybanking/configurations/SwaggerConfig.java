package com.qbitum.ifinitybanking.configurations;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi trainingJobsOpenApi(
        @Value("${springdoc.version}") String appVersion
    ) {
        String[] paths = { "/**/**" };
        return GroupedOpenApi
            .builder()
            .group("sampleTemplate")
            .addOpenApiCustomizer(
                openApi ->
                    openApi.info(
                        new Info()
                            .title("Template Service Sample API")
                            .version(appVersion)
                    )
            )
            .pathsToMatch(paths)
            .build();
    }
}
