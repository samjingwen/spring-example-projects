package io.samjingwen.multitenantjdbc;

import java.util.HashMap;
import java.util.function.Supplier;
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
    return new MultiTenantUser(username, "password", tenantId);
  }

  @Bean
  UserDetailsService userDetailsService() {
    Supplier<User> rob = () -> createUser("rwinch", 1);
    Supplier<User> josh = () -> createUser("jlong", 2);

    var users = new HashMap<String, Supplier<User>>();
    users.put("rwinch", rob);
    users.put("jlong", josh);

    return username -> {
      var user = users.getOrDefault(username, null);
      if (user == null) throw new UsernameNotFoundException("Could not find " + username + "!");
      return user.get();
    };
  }
}
