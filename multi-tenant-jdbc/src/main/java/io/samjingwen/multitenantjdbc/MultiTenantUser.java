package io.samjingwen.multitenantjdbc;

import java.util.List;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

class MultiTenantUser extends User {

  @Getter private final Integer tenantId;

  public MultiTenantUser(
      String username,
      String password,
      boolean enabled,
      boolean accountNonExpired,
      boolean credentialsNonExpired,
      boolean accountNonLocked,
      Integer tenantId) {
    super(
        username,
        PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password),
        enabled,
        accountNonExpired,
        credentialsNonExpired,
        accountNonLocked,
        List.of(new SimpleGrantedAuthority("USER")));
    this.tenantId = tenantId;
  }
}
