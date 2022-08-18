package io.samjingwen.multitenantjdbc;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Slf4j
@Configuration
class DataSourceConfiguration {
  @Bean
  @Primary
  DataSource multiTenantDataSource(Map<String, DataSource> dataSources) {
    var prefix = "ds";
    var map =
        dataSources.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(prefix))
            .collect(
                Collectors.toMap(
                    entry -> (Object) Integer.parseInt(entry.getKey().substring(prefix.length())),
                    entry -> (Object) entry.getValue()));

    map.forEach(
        (tenantId, dataSource) -> {
          var initializer =
              new ResourceDatabasePopulator(
                  new ClassPathResource("schema.sql"),
                  new ClassPathResource(prefix + tenantId + "-data.sql"));
          initializer.execute((DataSource) dataSource);
          log.info("initialized dataSource for " + tenantId);
        });

    var mds = new MultiTenantDataSource();
    mds.setTargetDataSources(map);
    return mds;
  }

  @Bean
  DataSource ds1() {
    return dataSource(5433);
  }

  @Bean
  DataSource ds2() {
    return dataSource(5434);
  }

  private static DataSource dataSource(int port) {
    var dataSourceProperties = new DataSourceProperties();
    dataSourceProperties.setUsername("user");
    dataSourceProperties.setPassword("password");
    dataSourceProperties.setUrl("jdbc:postgresql://localhost:" + port + "/user");
    return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }
}
