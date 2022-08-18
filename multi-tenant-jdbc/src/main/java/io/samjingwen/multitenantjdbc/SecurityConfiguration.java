package io.samjingwen.multitenantjdbc;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }

  private static User createUser(String username, Integer tenantId) {
    return new MultiTenantUser(username, "password", true, true, true, true, tenantId);
  }

  @Bean
  UserDetailsService userDetailsService() {
    var rob = createUser("rwinch", 1);
    var josh = createUser("jlong", 2);

    var users =
        Stream.of(josh, rob).collect(Collectors.toMap(User::getUsername, Function.identity()));

    return username -> {
      var user = users.getOrDefault(username, null);
      if (user == null) throw new UsernameNotFoundException("Could not find " + username + "!");
      return user;
    };
  }
}
