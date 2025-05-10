package br.tech.oe.plan.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "groups")
public class GroupModel {

    @Column(unique = true, insertable = false, updatable = false, columnDefinition = "serial")
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, insertable = false, updatable = false)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(
            name = "owner_uuid",
            referencedColumnName = "uuid",
            foreignKey = @ForeignKey(name = "fk_groups_user_uuid"),
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel owner;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private String status;

    @Column(columnDefinition = "text[]")
    private List<String> tags;

    @Column
    private String color;

    @Column
    private String category;

    @Column
    private Long visibility;

    @Column
    private Long accessLevel;

    @Column
    private Instant archivedAt;

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

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getVisibility() {
        return visibility;
    }

    public void setVisibility(Long visibility) {
        this.visibility = visibility;
    }

    public Long getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Long accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Instant getArchivedAt() {
        return archivedAt;
    }

    public void setArchivedAt(Instant archivedAt) {
        this.archivedAt = archivedAt;
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
