package de.ditti4.restapiissuemanager.developer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class DeveloperRepositoryConfiguration implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Developer.class);
        config.getExposureConfiguration()
                .forDomainType(Developer.class)
                .disablePutForCreation();
    }
}
