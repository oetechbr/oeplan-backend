package br.tech.oe.plan.service;

import br.tech.oe.plan.controller.v1.filters.CommentFilter;
import br.tech.oe.plan.dto.comment.CommentDTO;
import br.tech.oe.plan.dto.comment.CreateCommentDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface CommentService extends BaseService<CommentDTO, CommentFilter> {
    List<CommentDTO> findAll(UUID taskUuid, CommentFilter filters);

    CommentDTO findById(UUID taskUuid, UUID uuid);

    CommentDTO save(UUID taskUuid, CreateCommentDTO commentDto);

    @Transactional
    void delete(UUID commentUuid);
}