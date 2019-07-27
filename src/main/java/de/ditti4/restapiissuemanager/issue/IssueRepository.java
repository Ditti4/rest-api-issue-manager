package de.ditti4.restapiissuemanager.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "issues", path = "issues")
interface IssueRepository extends JpaRepository<Issue, Long> {
}
