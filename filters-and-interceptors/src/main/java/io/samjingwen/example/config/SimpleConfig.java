package io.samjingwen.example.config;

import io.samjingwen.example.interceptor.SimpleInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class SimpleConfig implements WebMvcConfigurer {

  private final SimpleInterceptor simpleInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(simpleInterceptor);
  }
}
