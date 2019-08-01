package de.ditti4.restapiissuemanager.week;

import de.ditti4.restapiissuemanager.developer.DeveloperRepository;
import de.ditti4.restapiissuemanager.issue.IssueRepository;
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
import java.util.Optional;

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
        newWeek = repository.save(newWeek);

        // The following code is pretty much copied from RepositoryEntityController's createAndReturn method

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
