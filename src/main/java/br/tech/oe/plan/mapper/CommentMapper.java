package br.tech.oe.plan.mapper;

import br.tech.oe.plan.dto.comment.CommentDTO;
import br.tech.oe.plan.dto.comment.UserCommentDTO;
import br.tech.oe.plan.model.CommentModel;
import br.tech.oe.plan.model.UserModel;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {
    public static CommentDTO toDTO(CommentModel model) {
        CommentDTO dto = new CommentDTO();
        dto.setUuid(model.getUuid());
        dto.setTaskId(model.getTask().getUuid());
        dto.setUser(toUserCommentDTO(model.getUser()));
        dto.setContent(model.getContent());
        dto.setVisible(model.isVisible());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        return dto;
    }

    public static List<CommentDTO> toDTO(List<CommentModel> models) {
        return models.stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static UserCommentDTO toUserCommentDTO(UserModel model) {
        UserCommentDTO dto = new UserCommentDTO();
        dto.setUuid(model.getUuid());
        dto.setUsername(model.getUsername());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setAvatarUrl(model.getAvatarUrl());
        return dto;
    }
}
