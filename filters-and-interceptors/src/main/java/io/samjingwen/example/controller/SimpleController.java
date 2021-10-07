package io.samjingwen.example.controller;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SimpleController {

  @GetMapping("testing")
  public CompletableFuture<String> handler() {
    log.info("SimpleController#handler called. Thread: " + Thread.currentThread().getName());
    return CompletableFuture.supplyAsync(
        () -> {
          log.info(
              "SimpleController#async task started. Thread: " + Thread.currentThread().getName());
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          log.info(
              "SimpleController#async task finished. Thread: " + Thread.currentThread().getName());
          return "hello world";
        });
  }
}
