package org.samjingwen.snsexample.model;

import lombok.Getter;
import lombok.Value;

@Value(staticConstructor = "of")
@Getter
public class PublishResponse {
    int status;
    String errorMessage;
}
