package br.tech.oe.plan.controller.v1.interfaces;

import br.tech.oe.plan.dto.comment.CommentDTO;
import br.tech.oe.plan.dto.comment.CreateCommentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface CommentController {
    @GetMapping("/{taskUuid}/comments")
    ResponseEntity<List<CommentDTO>> findAllComments(@PathVariable UUID taskUuid);

    @GetMapping("/{taskUuid}/comments/{uuid}")
    ResponseEntity<CommentDTO> findCommentById(@PathVariable UUID taskUuid, @PathVariable UUID uuid);

    @PostMapping("/{taskUuid}/comments")
    ResponseEntity<CommentDTO> saveComment(@RequestBody CreateCommentDTO commentDTO, @PathVariable UUID taskUuid);

    @DeleteMapping("/{taskUuid}/comments/{uuid}")
    ResponseEntity<Void> deleteComment(@PathVariable UUID uuid);
}
