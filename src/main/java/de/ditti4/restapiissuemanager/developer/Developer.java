package de.ditti4.restapiissuemanager.developer;

import de.ditti4.restapiissuemanager.issue.Issue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "developer")
    private List<Issue> issues = new ArrayList<>();

    public Developer() {}

    public Developer(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Issue> getIssues() {
        return issues;
    }
}
