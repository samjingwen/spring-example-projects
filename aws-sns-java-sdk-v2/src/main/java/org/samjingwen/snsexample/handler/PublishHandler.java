package org.samjingwen.snsexample.handler;

import lombok.RequiredArgsConstructor;
import org.samjingwen.snsexample.model.PublishResponse;
import org.samjingwen.snsexample.processor.PublishRequestProcessor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PublishHandler {

  private final PublishRequestProcessor publishRequestProcessor;

  @PostMapping("/publish")
  public Mono<ServerResponse> publishToSns(ServerRequest serverRequest) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(PublishResponse.of(200, "Success!")));
  }
}
