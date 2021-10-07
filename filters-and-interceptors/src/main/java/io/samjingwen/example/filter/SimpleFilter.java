package io.samjingwen.example.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    log.info("SimpleFilter#doFilter started. Thread: " + Thread.currentThread().getName());
    chain.doFilter(request, response);
    log.info("SimpleFilter#doFilter finished. Thread: " + Thread.currentThread().getName());
  }
}
