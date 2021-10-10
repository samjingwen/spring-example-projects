package org.samjingwen.snsexample.controller;

import lombok.RequiredArgsConstructor;
import org.samjingwen.snsexample.model.PublishRequest;
import org.samjingwen.snsexample.model.PublishResponse;
import org.samjingwen.snsexample.processor.PublishRequestProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.http.HttpStatusCode;

@RestController
@RequiredArgsConstructor
public class PublishController {

    private final PublishRequestProcessor publishRequestProcessor;

    @PostMapping("/publish")
    public ResponseEntity<PublishResponse> publishToSns(PublishRequest publishRequest){

        return ResponseEntity.ok(PublishResponse.of(HttpStatusCode.OK, ""));
    }
}
