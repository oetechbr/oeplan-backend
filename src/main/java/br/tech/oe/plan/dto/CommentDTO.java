package br.tech.oe.plan.dto;

import java.util.UUID;

public class CommentDTO extends BaseDTO {
    private UUID taskId;
    private UserCommentDTO user;
    private String content;
    private boolean isVisible;

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public UserCommentDTO getUser() {
        return user;
    }

    public void setUser(UserCommentDTO user) {
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
}
