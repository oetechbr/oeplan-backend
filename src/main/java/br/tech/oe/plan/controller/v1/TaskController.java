package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.controller.v1.interfaces.BaseController;
import br.tech.oe.plan.controller.v1.interfaces.CommentController;
import br.tech.oe.plan.dto.comment.CommentDTO;
import br.tech.oe.plan.dto.comment.CreateCommentDTO;
import br.tech.oe.plan.dto.task.CreateTaskDTO;
import br.tech.oe.plan.dto.task.TaskDTO;
import br.tech.oe.plan.dto.task.UpdateTaskDTO;
import br.tech.oe.plan.service.CommentService;
import br.tech.oe.plan.service.TaskService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController implements BaseController<TaskDTO>, CommentController {

    private final TaskService taskService;

    private final CommentService commentService;

    public TaskController(TaskService taskService, CommentService commentService) {
        this.taskService = taskService;
        this.commentService = commentService;
    }

    @Override
    public ResponseEntity<List<TaskDTO>> findAll(HttpSession session) {
        return ResponseEntity.ok(taskService.findAll());
    }

    @Override
    public ResponseEntity<TaskDTO> findById(UUID uuid) {
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

    @Override
    public ResponseEntity<List<CommentDTO>> findAllComments(UUID taskUuid) {
        return ResponseEntity.ok(commentService.findAll(taskUuid));
    }

    @Override
    public ResponseEntity<CommentDTO> findCommentById(UUID taskUuid, UUID uuid) {
        return ResponseEntity.ok(commentService.findById(taskUuid, uuid));
    }

    @Override
    public ResponseEntity<CommentDTO> saveComment(CreateCommentDTO commentDTO, UUID taskUuid) {
        return ResponseEntity.ok(commentService.save(commentDTO, taskUuid));
    }

    @Override
    public ResponseEntity<Void> deleteComment(UUID uuid) {
        commentService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}


