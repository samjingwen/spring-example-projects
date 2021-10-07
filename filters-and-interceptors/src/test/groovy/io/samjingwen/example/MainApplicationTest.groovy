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

    ListAppender<ILoggingEvent> logWatcher;

    def setup() {
        this.logWatcher = new ListAppender<>();
        this.logWatcher.start()
        ((Logger) LoggerFactory.getLogger(SimpleController.class)).addAppender(this.logWatcher);
        ((Logger) LoggerFactory.getLogger(SimpleFilter.class)).addAppender(this.logWatcher);
        ((Logger) LoggerFactory.getLogger(SimpleInterceptor.class)).addAppender(this.logWatcher);
    }

    def "should show all logs"() {
        expect:
        def mvcResult = mockMvc.perform(get("/testing"))
                .andExpect(request().asyncStarted())
                .andReturn()

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string("hello world"))

        12 == this.logWatcher.list.size()
        this.logWatcher.list.get(0).getFormattedMessage().contains("SimpleFilter#doFilter started. Thread: ")
        this.logWatcher.list.get(1).getFormattedMessage().contains("SimpleInterceptor#preHandler called. Thread: ")
        this.logWatcher.list.get(2).getFormattedMessage().contains("SimpleController#handler called. Thread: ")
        this.logWatcher.list.get(3).getFormattedMessage().contains("SimpleController#async task started. Thread: ")
        this.logWatcher.list.get(4).getFormattedMessage().contains("SimpleInterceptor#afterConcurrentHandlingStarted called. Thread: ")
        this.logWatcher.list.get(5).getFormattedMessage().contains("SimpleFilter#doFilter finished. Thread: ")
        this.logWatcher.list.get(6).getFormattedMessage().contains("SimpleController#async task finished. Thread: ")
        this.logWatcher.list.get(7).getFormattedMessage().contains("SimpleFilter#doFilter started. Thread: ")
        this.logWatcher.list.get(8).getFormattedMessage().contains("SimpleInterceptor#preHandler called. Thread: ")
        this.logWatcher.list.get(9).getFormattedMessage().contains("SimpleInterceptor#postHandle called. Thread: ")
        this.logWatcher.list.get(10).getFormattedMessage().contains("SimpleInterceptor#afterCompletion called. Thread: ")
        this.logWatcher.list.get(11).getFormattedMessage().contains("SimpleFilter#doFilter finished. Thread: ")
    }
}
