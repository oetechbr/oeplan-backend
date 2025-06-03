package br.tech.oe.plan.dto.user;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class CreateUserInviteDTO {
    @NotNull
    private CreateUserDTO user;

    private Instant expiresAt;

    public CreateUserDTO getUser() {
        return user;
    }

    public void setUser(CreateUserDTO user) {
        this.user = user;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
