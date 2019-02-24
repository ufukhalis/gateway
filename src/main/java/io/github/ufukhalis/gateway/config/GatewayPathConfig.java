package io.github.ufukhalis.gateway.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="gateway.config")
public class GatewayPathConfig {

    private List<Mapping> mapping;

    @Data
    @EnableConfigurationProperties
    @ConfigurationProperties(prefix="gateway.config.mapping")
    public static class Mapping{

        private String target;
        private String uri;
    }

}
