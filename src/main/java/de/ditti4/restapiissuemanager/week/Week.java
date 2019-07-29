package de.ditti4.restapiissuemanager.week;

import de.ditti4.restapiissuemanager.issue.Issue;
import de.ditti4.restapiissuemanager.issue.IssueType;
import de.ditti4.restapiissuemanager.issue.story.Story;
import de.ditti4.restapiissuemanager.issue.story.StoryStatus;

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
                .filter(story -> story.getStatus() != StoryStatus.NEW)
                .map(story -> {
                    // If there's some estimate still unassigned or this week is
                    // not the last week assigned to the story, return the
                    // maximum workload per week.
                    if (story.getRemainingEstimate() > 0 ||
                            story.getWeeks().stream().
                                    anyMatch(week -> week.getStartDate().compareTo(this.getEndDate()) > 0)) {
                        return MAXIMUM_WORKLOAD_PER_WEEK;
                    } else {
                        // Otherwise, return the total estimate minus the
                        // workload taken care of by the previous weeks.
                        return story.getEstimate() - (MAXIMUM_WORKLOAD_PER_WEEK * (story.getWeeks().size() - 1));
                    }
                })
                .mapToInt(Integer::intValue)
                .sum();
    }
}
