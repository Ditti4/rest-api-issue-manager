package de.ditti4.restapiissuemanager.issue;

import de.ditti4.restapiissuemanager.developer.Developer;
import de.ditti4.restapiissuemanager.developer.DeveloperRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IssueTestData {
    @Bean
    CommandLineRunner initializeIssueTestData(IssueRepository repository) {
        return args -> {
            repository.save(new Issue("First issue", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eget sodales mauris, et finibus leo. Vivamus at aliquam nibh, sit amet interdum arcu. Aliquam erat volutpat."));
            repository.save(new Issue("Second issue", "Donec id finibus neque. Sed eu tellus vel tortor porttitor mollis. In ultricies vulputate lacus, id ornare nunc dignissim quis. Mauris non facilisis quam, nec dapibus velit."));
        };
    }
}
