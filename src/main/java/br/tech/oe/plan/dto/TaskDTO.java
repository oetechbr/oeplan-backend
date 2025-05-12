package br.tech.oe.plan.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class TaskDTO extends BaseDTO {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private UserTaskDTO assignedTo;
    private UserTaskDTO assignedBy;
    private String statusId;
    private String priorityId;
    private String visibilityId;
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

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(String priorityId) {
        this.priorityId = priorityId;
    }

    public String getVisibilityId() {
        return visibilityId;
    }

    public void setVisibilityId(String visibilityId) {
        this.visibilityId = visibilityId;
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
