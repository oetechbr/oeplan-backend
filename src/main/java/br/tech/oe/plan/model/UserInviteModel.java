package br.tech.oe.plan.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_invites")
public class UserInviteModel {

    @Column(unique = true, insertable = false, updatable = false, columnDefinition = "serial")
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, insertable = false, updatable = false)
    private UUID uuid;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String description;

    @Column(unique = true, nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(
            name = "user_uuid",
            referencedColumnName = "uuid",
            foreignKey = @ForeignKey(name = "fk_user_invites_user_uuid"),
            nullable = false
    )
    private UserModel user;

    @ManyToOne
    @JoinColumn(
            name = "invited_by",
            referencedColumnName = "uuid",
            foreignKey = @ForeignKey(name = "fk_user_invites_invited_by"),
            nullable = false
    )
    private UserModel invitedBy;

    @Column
    private Instant acceptedAt;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(UserModel invitedBy) {
        this.invitedBy = invitedBy;
    }

    public Instant getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Instant acceptedAt) {
        this.acceptedAt = acceptedAt;
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
