package de.ditti4.restapiissuemanager.developer;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class DeveloperResourceAssembler implements ResourceAssembler<Developer, Resource<Developer>> {
    @Override
    public Resource<Developer> toResource(Developer developer) {
        return new Resource<>(developer,
                linkTo(methodOn(DeveloperController.class).getDeveloperById(developer.getId())).withSelfRel(),
                linkTo(methodOn(DeveloperController.class).getAllDevelopers()).withRel("developers"));
    }
}
