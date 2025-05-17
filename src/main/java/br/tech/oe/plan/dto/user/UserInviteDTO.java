package br.tech.oe.plan.dto.user;

import br.tech.oe.plan.dto.SimpleUserDTO;
import br.tech.oe.plan.enums.InviteStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInviteDTO implements Serializable {
    private String token;
    private String description;
    private SimpleUserDTO invitedBy;
    private SimpleUserDTO invitedUser;
    private InviteStatus status;
    private Instant acceptedAt;
    private Instant expiresAt;
    private Instant createdAt;
    private Instant updatedAt;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SimpleUserDTO getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(SimpleUserDTO invitedBy) {
        this.invitedBy = invitedBy;
    }

    public SimpleUserDTO getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(SimpleUserDTO invitedUser) {
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
