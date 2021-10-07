package io.samjingwen.example

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import io.samjingwen.example.controller.SimpleController
import io.samjingwen.example.filter.SimpleFilter
import io.samjingwen.example.interceptor.SimpleInterceptor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class FiltersAndInterceptorsSpec extends Specification {

    @Autowired
    MockMvc mockMvc;

    ListAppender<ILoggingEvent> simpleControllerLogWatcher;
    ListAppender<ILoggingEvent> simpleFilterLogWatcher;
    ListAppender<ILoggingEvent> simpleInterceptorLogWatcher;

    def setup() {
        this.simpleControllerLogWatcher = new ListAppender<>();
        this.simpleFilterLogWatcher = new ListAppender<>();
        this.simpleInterceptorLogWatcher = new ListAppender<>();

        this.simpleControllerLogWatcher.start()
        this.simpleFilterLogWatcher.start()
        this.simpleInterceptorLogWatcher.start()

        ((Logger) LoggerFactory.getLogger(SimpleController.class)).addAppender(this.simpleControllerLogWatcher);
        ((Logger) LoggerFactory.getLogger(SimpleFilter.class)).addAppender(this.simpleFilterLogWatcher);
        ((Logger) LoggerFactory.getLogger(SimpleInterceptor.class)).addAppender(this.simpleInterceptorLogWatcher);
    }

    def "should show all logs"() {
        expect:
        def mvcResult = mockMvc.perform(get("/testing"))
                .andExpect(request().asyncStarted())
                .andReturn()

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string("hello world"))

        3 == this.simpleControllerLogWatcher.list.size()
        4 == this.simpleFilterLogWatcher.list.size()
        5 == this.simpleInterceptorLogWatcher.list.size()

        this.simpleControllerLogWatcher.list.get(0).getFormattedMessage().contains("SimpleController#handler called. Thread: ")
        this.simpleControllerLogWatcher.list.get(1).getFormattedMessage().contains("SimpleController#async task started. Thread: ")
        this.simpleControllerLogWatcher.list.get(2).getFormattedMessage().contains("SimpleController#async task finished. Thread: ")

        this.simpleFilterLogWatcher.list.get(0).getFormattedMessage().contains("SimpleFilter#doFilter started. Thread: ")
        this.simpleFilterLogWatcher.list.get(1).getFormattedMessage().contains("SimpleFilter#doFilter finished. Thread: ")
        this.simpleFilterLogWatcher.list.get(2).getFormattedMessage().contains("SimpleFilter#doFilter started. Thread: ")
        this.simpleFilterLogWatcher.list.get(3).getFormattedMessage().contains("SimpleFilter#doFilter finished. Thread: ")

        this.simpleInterceptorLogWatcher.list.get(0).getFormattedMessage().contains("SimpleInterceptor#preHandler called. Thread: ")
        this.simpleInterceptorLogWatcher.list.get(1).getFormattedMessage().contains("SimpleInterceptor#afterConcurrentHandlingStarted called. Thread: ")
        this.simpleInterceptorLogWatcher.list.get(2).getFormattedMessage().contains("SimpleInterceptor#preHandler called. Thread: ")
        this.simpleInterceptorLogWatcher.list.get(3).getFormattedMessage().contains("SimpleInterceptor#postHandle called. Thread: ")
        this.simpleInterceptorLogWatcher.list.get(4).getFormattedMessage().contains("SimpleInterceptor#afterCompletion called. Thread: ")
    }
}
