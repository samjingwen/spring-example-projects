package io.samjingwen.swaggervalidation.config;

import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "swagger")
@RequiredArgsConstructor
@Getter
public class SwaggerProperties {

  private final Set<String> files;
}
