package br.tech.oe.plan.model;

import br.tech.oe.plan.enums.InviteStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

    @Column
    private String description;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(
            name = "invited_by",
            referencedColumnName = "uuid",
            foreignKey = @ForeignKey(name = "fk_user_invites_invited_by"),
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel invitedBy;

    @ManyToOne
    @JoinColumn(
            name = "invited_user",
            referencedColumnName = "uuid",
            foreignKey = @ForeignKey(name = "fk_user_invites_invited_user"),
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel invitedUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InviteStatus status = InviteStatus.PENDING;

    @Column
    private Instant acceptedAt;

    @Column
    private Instant expiresAt;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserModel getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(UserModel invitedBy) {
        this.invitedBy = invitedBy;
    }

    public UserModel getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(UserModel invitedUser) {
        this.invitedUser = invitedUser;
    }

    public InviteStatus getStatus() {
        return status;
    }

    public void setStatus(InviteStatus status) {
        this.status = status;
    }

    public Instant getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Instant acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
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
