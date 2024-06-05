package com.qbitum.ifinitybanking.configurations;

import com.qbitum.ifinitybanking.annotations.aspect.AnnotationCombinationAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Config class for annotation beans
 */

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public AnnotationCombinationAspect annotationCombinationAspect() {
        return new AnnotationCombinationAspect();
    }
}
