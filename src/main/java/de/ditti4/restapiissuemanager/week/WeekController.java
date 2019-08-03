package de.ditti4.restapiissuemanager.week;

import de.ditti4.restapiissuemanager.developer.Developer;
import de.ditti4.restapiissuemanager.developer.DeveloperRepository;
import de.ditti4.restapiissuemanager.issue.IssueRepository;
import de.ditti4.restapiissuemanager.issue.IssueType;
import de.ditti4.restapiissuemanager.issue.story.Story;
import de.ditti4.restapiissuemanager.issue.story.StoryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.*;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RepositoryRestController
public class WeekController {
    @Autowired
    private WeekRepository repository;
    @Autowired
    private DeveloperRepository developerRepository;
    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private HttpHeadersPreparer headersPreparer;

    @PostMapping("weeks")
    public ResponseEntity<ResourceSupport> post(PersistentEntityResourceAssembler assembler, @RequestBody Week newWeek) throws URISyntaxException {
        // First, split up any stories which have an estimate larger than the maximum workload per week.
        List<Story> storiesWhichNeedToBeSplitUp = issueRepository.findAll().stream()
                .filter(issue -> issue.getType() == IssueType.STORY)
                .map(issue -> (Story) issue)
                .filter(story -> story.getStatus() == StoryStatus.ESTIMATED)
                .filter(story -> story.getEstimate() > Week.MAXIMUM_WORKLOAD_PER_WEEK)
                .collect(Collectors.toList());

        for (Story story : storiesWhichNeedToBeSplitUp) {
            int amountOfSubStories = story.getEstimate() / Week.MAXIMUM_WORKLOAD_PER_WEEK;
            if (story.getEstimate() % Week.MAXIMUM_WORKLOAD_PER_WEEK != 0) {
                amountOfSubStories++;
            }
            for (int i = 0; i < amountOfSubStories; i++) {
                int subStoryWorkload = 0;
                if (story.getEstimate() - Week.MAXIMUM_WORKLOAD_PER_WEEK * (i + 1) > 0) {
                    subStoryWorkload = Week.MAXIMUM_WORKLOAD_PER_WEEK;
                } else {
                    subStoryWorkload = story.getEstimate() - Week.MAXIMUM_WORKLOAD_PER_WEEK * i;
                }
                issueRepository.save(new Story(story.getTitle() + " (1 of " + amountOfSubStories + " sub tasks)", story.getDescription(), subStoryWorkload));
            }
            issueRepository.delete(story);
        }

        List<Story> remainingStories = issueRepository.findAll().stream()
                .filter(issue -> issue.getType() == IssueType.STORY)
                .map(issue -> (Story) issue)
                .filter(story -> story.getStatus() == StoryStatus.ESTIMATED)
                .filter(story -> story.getWeek() == null)
                .sorted(Comparator.comparingInt(Story::getEstimate).reversed()) // Let's tackle the largest ones first.
                .collect(Collectors.toList());


        for (Developer developer : developerRepository.findAll()) {
            int workload = 0;
            List<Story> assignedStories = new ArrayList<>();
            for (Story story : remainingStories) {
                if (workload + story.getEstimate() <= Week.MAXIMUM_WORKLOAD_PER_WEEK) {
                    story.setDeveloper(developer);
                    story.setWeek(newWeek);
                    workload += story.getEstimate();
                    newWeek.getIssues().add(story);
                    assignedStories.add(story);
                }
                if (workload == Week.MAXIMUM_WORKLOAD_PER_WEEK) {
                    break;
                }
            }
            remainingStories.removeAll(assignedStories);
        }

        newWeek = repository.save(newWeek);
        issueRepository.saveAll(newWeek.getIssues());

        // The following code is pretty much copied from RepositoryEntityController's createAndReturn method.

        Optional<PersistentEntityResource> resource = Optional
                .ofNullable(assembler.toFullResource(newWeek));

        HttpHeaders headers = headersPreparer.prepareHeaders(resource);
        addLocationHeader(headers, assembler, newWeek);

        return ControllerUtils.toResponseEntity(HttpStatus.CREATED, headers, resource);
    }

    private void addLocationHeader(HttpHeaders headers, PersistentEntityResourceAssembler assembler, Object source) throws URISyntaxException {
        String selfLink = assembler.getSelfLinkFor(source).getHref();
        headers.setLocation(new URI(selfLink));
    }
}
