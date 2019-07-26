package de.ditti4.restapiissuemanager.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "developers")
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
