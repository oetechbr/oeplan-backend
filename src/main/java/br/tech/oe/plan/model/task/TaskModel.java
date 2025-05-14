package br.tech.oe.plan.model.task;

import br.tech.oe.plan.enums.TaskPriority;
import br.tech.oe.plan.enums.TaskStatus;
import br.tech.oe.plan.enums.TaskVisibility;
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

    @Column(nullable = false)
    private String description;

    @Column
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskVisibility visibility;

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
