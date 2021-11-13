package org.samjingwen.snsexample.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.samjingwen.snsexample.handler.PublishHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class RouterConfig {
  @Bean
  public RouterFunction<ServerResponse> route(PublishHandler publishHandler) {
    return RouterFunctions.route(
        GET("/publish").and(accept(MediaType.APPLICATION_JSON)), publishHandler::publishToSns);
  }
}
