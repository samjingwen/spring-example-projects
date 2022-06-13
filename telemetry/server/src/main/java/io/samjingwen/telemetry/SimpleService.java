package io.samjingwen.telemetry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SimpleService {
    private final WebClient webClient;

    public CompletableFuture<Todo> getTodo() {
        return webClient.get().retrieve().bodyToMono(Todo.class).toFuture();
    }
}
