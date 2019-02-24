package io.github.ufukhalis.gateway.controller;

import io.github.ufukhalis.gateway.service.GatewayService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {

    @Autowired
    GatewayService gatewayService;

    @Timed
    @RequestMapping(value = "/**")
    public Mono<String> redirect(ServerHttpRequest request) {
        return gatewayService.redirect(request);
    }

}
