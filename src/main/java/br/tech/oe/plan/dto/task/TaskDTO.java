package br.tech.oe.plan.dto.task;

import br.tech.oe.plan.dto.BaseDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class TaskDTO extends BaseDTO {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private UserTaskDTO assignedTo;
    private UserTaskDTO assignedBy;
    private String status;
    private String priority;
    private String visibility;
    private List<String> tags;
    private Instant archivedAt;
    private Instant completedAt;

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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public UserTaskDTO getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UserTaskDTO assignedTo) {
        this.assignedTo = assignedTo;
    }

    public UserTaskDTO getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(UserTaskDTO assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Instant getArchivedAt() {
        return archivedAt;
    }

    public void setArchivedAt(Instant archivedAt) {
        this.archivedAt = archivedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }
}
