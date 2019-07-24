package de.ditti4.restapiissuemanager.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class DeveloperController {
    @Autowired
    private DeveloperRepository repository;
    @Autowired
    private DeveloperResourceAssembler assembler;

    @GetMapping("/developers")
    public Resources<Resource<Developer>> getAllDevelopers() {
        List<Resource<Developer>> developers = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(developers,
                linkTo(methodOn(DeveloperController.class).getAllDevelopers()).withSelfRel());
    }

    @GetMapping("/developers/{id}")
    public Resource<Developer> getDeveloperById(@PathVariable long id) {
        Developer developer = repository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException(id));
        return assembler.toResource(developer);
    }

    @PostMapping("/developers")
    public ResponseEntity<Resource<Developer>> addDeveloper(@RequestBody Developer developer) throws URISyntaxException {
        Resource<Developer> resource = assembler.toResource(repository.save(developer));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/developers/{id}")
    public Resource<Developer> updateDeveloper(@RequestBody Developer newDeveloper, @PathVariable long id) {
        return repository.findById(id)
                .map(developer -> {
                    developer.setName(newDeveloper.getName());
                    return assembler.toResource(repository.save(developer));
                })
                .orElseThrow(() -> new DeveloperNotFoundException(id));
    }

    @DeleteMapping("/developers/{id}")
    public ResponseEntity<Object> deleteDeveloperById(@PathVariable long id) {
        return repository.findById(id)
                .map(developer -> {
                    repository.delete(developer);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
