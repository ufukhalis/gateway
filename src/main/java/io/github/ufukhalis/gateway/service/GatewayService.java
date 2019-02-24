package io.github.ufukhalis.gateway.service;

import io.github.ufukhalis.gateway.config.GatewayPathConfig;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GatewayService {

    @Autowired
    @Qualifier("gatewayClient")
    WebClient.Builder webClientBuilder;

    @Autowired
    GatewayPathConfig configuration;

    public Mono<String> redirect(ServerHttpRequest request) {

        final String gatewayPath = request.getPath().toString();

        return findMapping(gatewayPath).map(mapping -> {
            String redirectionPath = gatewayPath.replace(mapping.getTarget(), "");

            String url = mapping.getUri() + redirectionPath;
            String queryParams = Option.of(request.getURI().getQuery()).map(params -> "?" + params).getOrElse("");
            MediaType contentType = request.getHeaders().getContentType();
            Flux<DataBuffer> body = request.getBody();

            return makeRequest(request.getMethod(), url, queryParams, body, contentType);
        }).getOrElse(Mono.error(new RuntimeException("Mapping Not Found, path : " + gatewayPath)));
    }

    private Option<GatewayPathConfig.Mapping> findMapping(String gatewayPath) {
        return List.ofAll(configuration.getMapping())
                .find(mapping -> gatewayPath.startsWith(mapping.getTarget()));
    }

    private Mono<String> makeRequest(HttpMethod method, String url, String queryParams, Flux<DataBuffer> body, MediaType contentType) {
        return webClientBuilder.baseUrl(url).build()
                .method(method)
                .uri(queryParams)
                .contentType(contentType)
                .body(BodyInserters.fromDataBuffers(body))
                .retrieve()
                .bodyToMono(String.class);
    }

}
