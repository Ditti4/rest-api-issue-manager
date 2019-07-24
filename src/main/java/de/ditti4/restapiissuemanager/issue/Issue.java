package de.ditti4.restapiissuemanager.issue;

import de.ditti4.restapiissuemanager.developer.Developer;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Issue {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    @CreationTimestamp
    private Date creationDate;

    @ManyToOne
    private Developer developer;

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

    public String getDescription() {
        return description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }
}
