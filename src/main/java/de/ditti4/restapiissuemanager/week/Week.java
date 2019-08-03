package de.ditti4.restapiissuemanager.week;

import de.ditti4.restapiissuemanager.issue.Issue;
import de.ditti4.restapiissuemanager.issue.IssueType;
import de.ditti4.restapiissuemanager.issue.story.Story;
import de.ditti4.restapiissuemanager.issue.story.StoryStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Week {
    public static final int MAXIMUM_WORKLOAD_PER_WEEK = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;

    @OneToMany(mappedBy = "week")
    private List<Issue> issues = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return startDate.plusDays(4);
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public Integer getTotalWorkload() {
        return getIssues().stream()
                .filter(issue -> issue.getType() == IssueType.STORY)
                .map(issue -> (Story) issue)
                .filter(story -> story.getStatus() != StoryStatus.NEW)
                .map(Story::getEstimate)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
