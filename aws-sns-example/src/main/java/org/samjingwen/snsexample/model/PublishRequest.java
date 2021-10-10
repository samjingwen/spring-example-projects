package org.samjingwen.snsexample.model;

import lombok.Getter;
import lombok.Value;

@Value(staticConstructor = "of")
@Getter
public class PublishRequest {

    String platformArn;
    String endpointArn;
    String message;
}
