package de.ditti4.restapiissuemanager.issue.bug;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import de.ditti4.restapiissuemanager.issue.Issue;
import de.ditti4.restapiissuemanager.issue.IssueType;

import javax.persistence.Entity;

@Entity
@JsonTypeName("BUG")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bug extends Issue {
    private BugStatus status;
    private BugPriority priority;

    public Bug() {
        this.initDefaultValues();
    }

    public Bug(String title, String description) {
        super(title, description);
        this.initDefaultValues();
    }

    public Bug(String title, String description, BugPriority priority) {
        this(title, description);
        this.priority = priority;
    }

    private void initDefaultValues() {
        this.type = IssueType.BUG;
        this.status = BugStatus.NEW;
        this.priority = BugPriority.MINOR;
    }

    public BugPriority getPriority() {
        return priority;
    }

    public void setPriority(BugPriority priority) {
        this.priority = priority;
    }
}
