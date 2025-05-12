package br.tech.oe.plan.dto.comment;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateCommentDTO {
    private UUID userId;

    @NotNull
    private String content;

    private boolean isVisible = true;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
