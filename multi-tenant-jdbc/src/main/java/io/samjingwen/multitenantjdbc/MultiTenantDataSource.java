package io.samjingwen.multitenantjdbc;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
class MultiTenantDataSource extends AbstractRoutingDataSource {

  private final AtomicBoolean initialized = new AtomicBoolean();

  @Override
  protected DataSource determineTargetDataSource() {
    if (this.initialized.compareAndSet(false, true)) {
      this.afterPropertiesSet();
    }
    return super.determineTargetDataSource();
  }

  @Override
  protected Object determineCurrentLookupKey() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.getPrincipal() instanceof MultiTenantUser user) {
      var tenantId = user.getTenantId();
      log.info("The tenant id is " + tenantId);
      return tenantId;
    }

    return null;
  }
}
