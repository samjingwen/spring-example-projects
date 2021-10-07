package io.samjingwen.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class SimpleInterceptor implements AsyncHandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info("SimpleInterceptor#preHandler called. Thread: " + Thread.currentThread().getName());
    return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    log.info("SimpleInterceptor#postHandle called. Thread: " + Thread.currentThread().getName());
    AsyncHandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    log.info("SimpleInterceptor#afterCompletion called. Thread: " + Thread.currentThread().getName());
    AsyncHandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }

  @Override
  public void afterConcurrentHandlingStarted(
      HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    log.info("SimpleInterceptor#afterConcurrentHandlingStarted called. Thread: " + Thread.currentThread().getName());
    AsyncHandlerInterceptor.super.afterConcurrentHandlingStarted(request, response, handler);
  }
}
