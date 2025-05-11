package br.tech.oe.plan.service;

import br.tech.oe.plan.dto.CommentDTO;
import br.tech.oe.plan.dto.CreateCommentDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface CommentService extends BaseService<CommentDTO> {
    List<CommentDTO> findAll(UUID taskUuid);

    CommentDTO findById(UUID taskUuid, UUID uuid);

    CommentDTO save(CreateCommentDTO commentDto, UUID taskUuid);

    @Transactional
    void delete(UUID commentUuid);
}