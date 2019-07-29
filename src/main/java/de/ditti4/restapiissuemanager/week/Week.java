package de.ditti4.restapiissuemanager.week;

import de.ditti4.restapiissuemanager.issue.Issue;
import de.ditti4.restapiissuemanager.issue.IssueType;
import de.ditti4.restapiissuemanager.issue.story.Story;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Week {
    public static final int MAXIMUM_WORKLOAD_PER_WEEK = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;

    @ManyToMany
    @JoinTable(name = "week_issue",
            joinColumns = @JoinColumn(name = "week_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "issue_id", referencedColumnName = "id"))
    List<Issue> issues;

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
                .map(Story::getEstimate)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
