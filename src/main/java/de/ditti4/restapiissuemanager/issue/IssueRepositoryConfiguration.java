package de.ditti4.restapiissuemanager.issue;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class IssueRepositoryConfiguration implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Issue.class);
        config.getExposureConfiguration()
                .forDomainType(Issue.class)
                .disablePutForCreation();
    }
}
