package com.user.service.config;

import brave.Tracing;
import brave.http.HttpTracing;
import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BraveConfig {

    @Bean
    public Tracing tracing() {
        return Tracing.newBuilder()
                .localServiceName("user-service")
                .sampler(Sampler.create(1.0f))
                .build();
    }

    @Bean
    public HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }
}
