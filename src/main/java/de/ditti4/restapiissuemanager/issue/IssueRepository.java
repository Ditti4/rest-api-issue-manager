package de.ditti4.restapiissuemanager.issue;

import org.springframework.data.jpa.repository.JpaRepository;

interface IssueRepository extends JpaRepository<Issue, Long> {}
