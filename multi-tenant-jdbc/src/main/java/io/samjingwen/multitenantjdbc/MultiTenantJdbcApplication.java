package io.samjingwen.multitenantjdbc;

import static org.springframework.web.servlet.function.RouterFunctions.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@SpringBootApplication
public class MultiTenantJdbcApplication {

  public static void main(String[] args) {
    SpringApplication.run(MultiTenantJdbcApplication.class, args);
  }

  @Bean
  RouterFunction<ServerResponse> routes(JdbcTemplate jdbcTemplate) {
    return route()
        .GET(
            "/customers",
            request -> {
              var results =
                  jdbcTemplate.query(
                      "select * from customer;",
                      (resultSet, rowNum) ->
                          new Customer(resultSet.getInt("id"), resultSet.getString("name")));
              return ServerResponse.ok().body(results);
            })
        .build();
  }
}
