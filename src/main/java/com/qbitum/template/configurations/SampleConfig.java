package com.qbitum.template.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**This is the sample configuration class if you want to add your own configurations
 * please see the below documentation on how to add
 * @see <a href="https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-developing-auto-configuration.html">Documentation</a>'
 */

@Getter
@Setter
@Configuration
@ConfigurationProperties("sample")
public class SampleConfig {
    private String name;
    private String message;
}
