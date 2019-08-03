package de.ditti4.restapiissuemanager.developer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeveloperTestData {
    @Bean
    CommandLineRunner initializeDeveloperTestData(DeveloperRepository repository) {
        return args -> {
            repository.save(new Developer("King Benbow"));
            repository.save(new Developer("Nettie Grady"));
        };
    }
}
