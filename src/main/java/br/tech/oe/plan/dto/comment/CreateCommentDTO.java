package br.tech.oe.plan.dto.comment;

import jakarta.validation.constraints.NotNull;

public class CreateCommentDTO {
    @NotNull
    private String content;

    private boolean isVisible = true;

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
