package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.comment.CommentDTO;
import br.tech.oe.plan.dto.comment.CreateCommentDTO;
import br.tech.oe.plan.dto.task.CreateTaskDTO;
import br.tech.oe.plan.dto.task.TaskDTO;
import br.tech.oe.plan.dto.task.UpdateTaskDTO;
import br.tech.oe.plan.service.CommentService;
import br.tech.oe.plan.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Endpoints for managing tasks")
public class TaskController {

    private final TaskService taskService;

    private final CommentService commentService;

    public TaskController(TaskService taskService, CommentService commentService) {
        this.taskService = taskService;
        this.commentService = commentService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all tasks")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<List<TaskDTO>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get single tasks")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<TaskDTO> findById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(taskService.findById(uuid));
    }

    @PostMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Save single task")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<TaskDTO> save(@RequestBody @Valid CreateTaskDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(dto));
    }

    @PatchMapping(
            value = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Patch single task")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<TaskDTO> patch(@PathVariable UUID uuid, @RequestBody @Valid UpdateTaskDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.patch(uuid, dto));
    }

    @DeleteMapping(value = "/{uuid}")
    @Operation(summary = "Delete single task")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        taskService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{taskUuid}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all comments")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<List<CommentDTO>> findAllComments(@PathVariable UUID taskUuid) {
        return ResponseEntity.ok(commentService.findAll(taskUuid));
    }

    @GetMapping(value = "/{taskUuid}/comments/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get single comment")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<CommentDTO> findCommentById(@PathVariable UUID taskUuid, @PathVariable UUID uuid) {
        return ResponseEntity.ok(commentService.findById(taskUuid, uuid));
    }

    @PostMapping(
            value = "/{taskUuid}/comments",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Save single comment")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<CommentDTO> saveComment(
            @PathVariable UUID taskUuid,
            @RequestBody @Valid CreateCommentDTO dto
    ) {
        return ResponseEntity.ok(commentService.save(taskUuid, dto));
    }

    @DeleteMapping(value = "/{taskUuid}/comments/{uuid}")
    @Operation(summary = "Delete single comment")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> deleteComment(UUID uuid) {
        commentService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}


