package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.controller.v1.filters.CommentFilter;
import br.tech.oe.plan.dto.comment.CommentDTO;
import br.tech.oe.plan.dto.comment.CreateCommentDTO;
import br.tech.oe.plan.exception.ForbiddenException;
import br.tech.oe.plan.exception.ItemNotFoundException;
import br.tech.oe.plan.mapper.CommentMapper;
import br.tech.oe.plan.model.CommentModel;
import br.tech.oe.plan.repository.CommentRepository;
import br.tech.oe.plan.repository.TaskRepository;
import br.tech.oe.plan.security.utils.SecurityUtils;
import br.tech.oe.plan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<CommentDTO> findAll(CommentFilter filters) {
        List<CommentModel> res = commentRepository.findAll();
        return CommentMapper.toDTO(res);
    }

    @Override
    public List<CommentDTO> findAll(UUID taskUuid, CommentFilter filters) {
        List<CommentModel> res = commentRepository.findAllByTaskUuid(taskUuid);
        return CommentMapper.toDTO(res);
    }

    @Override
    public CommentDTO findById(UUID taskUuid, UUID uuid) {
        var res = commentRepository.findByUuidAndTaskUuid(uuid, taskUuid)
                .orElseThrow(ItemNotFoundException::new);
        return CommentMapper.toDTO(res);
    }

    @Override
    public CommentDTO findById(UUID uuid) {
        var res = commentRepository.findById(uuid).orElseThrow(ItemNotFoundException::new);
        return CommentMapper.toDTO(res);
    }

    @Override
    public CommentDTO save(UUID taskUuid, CreateCommentDTO commentDto) {
        var user = SecurityUtils.getAuthenticatedOrThrow();

        var task = taskRepository.findById(taskUuid);
        if (task.isEmpty()) throw new ItemNotFoundException("Task doesn't exist");

        var model = new CommentModel();
        model.setTask(task.get());
        model.setUser(user);
        model.setContent(commentDto.getContent());
        model.setVisible(commentDto.getIsVisible());

        var res = commentRepository.save(model);
        return CommentMapper.toDTO(res);
    }

    @Override
    public void delete(UUID commentUuid) {
        var user = SecurityUtils.getAuthenticatedOrThrow();

        var comment = commentRepository.findById(commentUuid);
        if (comment.isEmpty()) {
            throw new ItemNotFoundException("Comment doesn't exist");
        }

        if (!comment.get().getUser().getUuid().equals(user.getUuid())) {
            throw new ForbiddenException("User doesn't has permission to delete this comment");
        }

        var deletedCommentUuid = commentRepository.deleteByUuidAndUserUuid(commentUuid, user.getUuid());
        if (deletedCommentUuid == 0) {
            throw new RuntimeException("Couldn't delete comment");
        }
    }
}
