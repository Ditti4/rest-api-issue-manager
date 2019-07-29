package de.ditti4.restapiissuemanager.week;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class WeekRepositoryConfiguration implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Week.class);
        config.getExposureConfiguration()
                .forDomainType(Week.class)
                .disablePutForCreation();
    }
}
