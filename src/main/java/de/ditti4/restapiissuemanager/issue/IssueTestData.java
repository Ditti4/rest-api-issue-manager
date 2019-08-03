package de.ditti4.restapiissuemanager.issue;

import de.ditti4.restapiissuemanager.issue.bug.Bug;
import de.ditti4.restapiissuemanager.issue.bug.BugPriority;
import de.ditti4.restapiissuemanager.issue.story.Story;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IssueTestData {
    @Bean
    CommandLineRunner initializeIssueTestData(IssueRepository repository) {
        return args -> {
            repository.save(new Bug("First issue", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eget sodales mauris, et finibus leo. Vivamus at aliquam nibh, sit amet interdum arcu. Aliquam erat volutpat.", BugPriority.MAJOR));
            repository.save(new Story("Story 1", "Story 1 description", 5));
            repository.save(new Story("Story 2", "Story 2 description", 4));
            repository.save(new Story("Story 3", "Story 3 description", 3));
            repository.save(new Story("Story 4", "Story 4 description", 2));
            repository.save(new Story("Story 5", "Story 5 description", 1));
            repository.save(new Story("Story 6", "Story 6 description", 1));
            repository.save(new Story("Story 7", "Story 7 description", 7));
            repository.save(new Story("Story 8", "Story 8 description", 9));
            repository.save(new Story("Story 9", "Story 9 description", 10));
            repository.save(new Story("Story 10", "Story 10 description", 20));
        };
    }
}
