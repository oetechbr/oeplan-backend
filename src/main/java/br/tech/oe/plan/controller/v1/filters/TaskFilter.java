package br.tech.oe.plan.controller.v1.filters;

import br.tech.oe.plan.enums.TaskPriority;
import br.tech.oe.plan.enums.TaskStatus;
import br.tech.oe.plan.enums.TaskVisibility;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskFilter {
    private UUID groupUuid;
    private String title;
    private LocalDateTime dueDate;
    private UUID assignedBy;
    private TaskStatus status;
    private TaskPriority priority;
    private TaskVisibility visibility;

    public UUID getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(UUID groupUuid) {
        this.groupUuid = groupUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public UUID getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(UUID assignedBy) {
        this.assignedBy = assignedBy;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(TaskVisibility visibility) {
        this.visibility = visibility;
    }
}
