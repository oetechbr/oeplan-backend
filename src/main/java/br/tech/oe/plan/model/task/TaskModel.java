package br.tech.oe.plan.model.task;

import br.tech.oe.plan.model.GroupModel;
import br.tech.oe.plan.model.user.UserModel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TaskModel {

    @Column(unique = true, insertable = false, updatable = false, columnDefinition = "serial")
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, insertable = false, updatable = false)
    private UUID uuid;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(
            name = "assigned_to",
            referencedColumnName = "uuid",
            foreignKey = @ForeignKey(name = "fk_tasks_assigned_to")
    )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private UserModel assignedTo;

    @ManyToOne
    @JoinColumn(
            name = "assigned_by",
            referencedColumnName = "uuid",
            foreignKey = @ForeignKey(name = "fk_tasks_assigned_by")
    )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private UserModel assignedBy;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "status_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_tasks_status_id"),
            nullable = false
    )
    private TaskStatusModel status;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "priority_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_tasks_priority_id"),
            nullable = false
    )
    private TaskPriorityModel priority;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "visibility_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_tasks_visibility_id"),
            nullable = false
    )
    private TaskVisibilityModel visibility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "group_uuid",
            referencedColumnName = "uuid",
            foreignKey = @ForeignKey(name = "fk_tasks_group_uuid")
    )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private GroupModel group;

    @Column(columnDefinition = "text[]")
    private List<String> tags;

    @Column
    private Instant archivedAt;

    @Column
    private Instant completedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @Column
    @UpdateTimestamp
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public UserModel getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UserModel assignedTo) {
        this.assignedTo = assignedTo;
    }

    public UserModel getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(UserModel assignedBy) {
        this.assignedBy = assignedBy;
    }

    public TaskStatusModel getStatus() {
        return status;
    }

    public void setStatus(TaskStatusModel status) {
        this.status = status;
    }

    public TaskPriorityModel getPriority() {
        return priority;
    }

    public void setPriority(TaskPriorityModel priority) {
        this.priority = priority;
    }

    public TaskVisibilityModel getVisibility() {
        return visibility;
    }

    public void setVisibility(TaskVisibilityModel visibility) {
        this.visibility = visibility;
    }

    public GroupModel getGroup() {
        return group;
    }

    public void setGroup(GroupModel group) {
        this.group = group;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
