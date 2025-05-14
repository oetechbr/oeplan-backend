package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.comment.CommentDTO;
import br.tech.oe.plan.dto.comment.CreateCommentDTO;
import br.tech.oe.plan.dto.task.CreateTaskDTO;
import br.tech.oe.plan.dto.task.TaskDTO;
import br.tech.oe.plan.dto.task.UpdateTaskDTO;
import br.tech.oe.plan.service.CommentService;
import br.tech.oe.plan.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    private final CommentService commentService;

    public TaskController(TaskService taskService, CommentService commentService) {
        this.taskService = taskService;
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TaskDTO> findById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(taskService.findById(uuid));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> save(@RequestBody @Valid CreateTaskDTO task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(task));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<TaskDTO> save(@PathVariable UUID uuid, @RequestBody @Valid UpdateTaskDTO task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.patch(uuid, task));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        taskService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{taskUuid}/comments")
    public ResponseEntity<List<CommentDTO>> findAllComments(@PathVariable UUID taskUuid) {
        return ResponseEntity.ok(commentService.findAll(taskUuid));
    }

    @GetMapping("/{taskUuid}/comments/{uuid}")
    public ResponseEntity<CommentDTO> findCommentById(@PathVariable UUID taskUuid, @PathVariable UUID uuid) {
        return ResponseEntity.ok(commentService.findById(taskUuid, uuid));
    }

    @PostMapping("/{taskUuid}/comments")
    public ResponseEntity<CommentDTO> saveComment(
            @PathVariable UUID taskUuid,
            @RequestBody @Valid CreateCommentDTO commentDTO
    ) {
        return ResponseEntity.ok(commentService.save(taskUuid, commentDTO));
    }

    @DeleteMapping("/{taskUuid}/comments/{uuid}")
    public ResponseEntity<Void> deleteComment(UUID uuid) {
        commentService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}


