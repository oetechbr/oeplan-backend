package br.tech.oe.plan.model;

import br.tech.oe.plan.model.task.TaskModel;
import br.tech.oe.plan.model.user.UserModel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class CommentModel {

    @Column(unique = true, insertable = false, updatable = false, columnDefinition = "serial")
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, insertable = false, updatable = false)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(
            name = "task_uuid",
            referencedColumnName = "uuid",
            foreignKey = @ForeignKey(name = "fk_comments_task_uuid")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TaskModel task;

    @ManyToOne
    @JoinColumn(
            name = "user_uuid",
            referencedColumnName = "uuid",
            foreignKey = @ForeignKey(name = "fk_comments_user_uuid")
    )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private UserModel user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    private boolean isVisible;

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

    public TaskModel getTask() {
        return task;
    }

    public void setTask(TaskModel task) {
        this.task = task;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
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
