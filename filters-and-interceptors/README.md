## A demo of filters and interceptors in Spring

### Output:
```
SimpleFilter#doFilter started. Thread: http-nio-8080-exec-1
SimpleInterceptor#preHandler called. Thread: http-nio-8080-exec-1
SimpleController#handler called. Thread: http-nio-8080-exec-1
SimpleController#async task started. Thread: ForkJoinPool.commonPool-worker-3
SimpleInterceptor#afterConcurrentHandlingStarted called. Thread: http-nio-8080-exec-1
SimpleFilter#doFilter finished. Thread: http-nio-8080-exec-1
SimpleController#async task finished. Thread: ForkJoinPool.commonPool-worker-3
SimpleInterceptor#preHandler called. Thread: http-nio-8080-exec-2
SimpleInterceptor#postHandle called. Thread: http-nio-8080-exec-2
SimpleInterceptor#afterCompletion called. Thread: http-nio-8080-exec-2
```