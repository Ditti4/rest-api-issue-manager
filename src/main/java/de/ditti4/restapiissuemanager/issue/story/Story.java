package de.ditti4.restapiissuemanager.issue.story;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import de.ditti4.restapiissuemanager.issue.Issue;
import de.ditti4.restapiissuemanager.issue.IssueType;

import javax.persistence.Entity;

@Entity
@JsonTypeName("STORY")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Story extends Issue {
    private Integer estimate;
    private StoryStatus status;

    public Story() {
        this.initDefaultValues();
    }

    public Story(String title, String description, Integer estimate) {
        super(title, description);
        this.initDefaultValues();
        this.setEstimate(estimate);
    }

    private void initDefaultValues() {
        this.type = IssueType.STORY;
        this.status = StoryStatus.NEW;
    }

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer estimate) {
        if (estimate != null && this.status == StoryStatus.NEW) {
            this.status = StoryStatus.ESTIMATED;
        } else if (estimate == null && this.status == StoryStatus.ESTIMATED) {
            this.status = StoryStatus.NEW;
        }
        this.estimate = estimate;
    }

    public StoryStatus getStatus() {
        return status;
    }

    public void setStatus(StoryStatus status) {
        // Only allow switching to status NEW if there's no estimate yet
        if (this.estimate == null || status != StoryStatus.NEW) {
            this.status = status;
        }
    }
}
