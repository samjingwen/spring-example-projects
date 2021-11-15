package io.samjingwen.swaggervalidation.config;

import com.atlassian.oai.validator.springmvc.OpenApiValidationFilter;
import javax.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiValidationConfig {
  @Bean
  public Filter validationFilter() {
    return new OpenApiValidationFilter(true, true);
  }
}
