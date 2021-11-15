package io.samjingwen.swaggervalidation.config;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.springmvc.OpenApiValidationInterceptor;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

  private final SwaggerProperties swaggerProperties;

  @Override
  public void addInterceptors(@Nonnull InterceptorRegistry registry) {
    for (String swaggerFile : swaggerProperties.getFiles()) {
      registry.addInterceptor(
          new OpenApiValidationInterceptor(
              OpenApiInteractionValidator.createFor(swaggerFile).build()));
    }
  }
}
