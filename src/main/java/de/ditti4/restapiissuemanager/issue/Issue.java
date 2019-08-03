package de.ditti4.restapiissuemanager.issue;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.ditti4.restapiissuemanager.developer.Developer;
import de.ditti4.restapiissuemanager.issue.bug.Bug;
import de.ditti4.restapiissuemanager.issue.story.Story;
import de.ditti4.restapiissuemanager.week.Week;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.time.ZonedDateTime;

@RestResource(path = "issues", rel = "issues")
@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bug.class),
        @JsonSubTypes.Type(value = Story.class)
})
public abstract class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @CreationTimestamp
    private ZonedDateTime creationDate;

    @ManyToOne
    private Developer developer;

    @ManyToOne
    private Week week;

    protected IssueType type;

    public Issue() {}

    public Issue(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public IssueType getType() {
        return type;
    }
}
