package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.controller.v1.interfaces.BaseController;
import br.tech.oe.plan.dto.TaskDTO;
import br.tech.oe.plan.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController implements BaseController<TaskDTO> {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ResponseEntity<List<TaskDTO>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @Override
    public ResponseEntity<TaskDTO> findById(UUID uuid) {
        return ResponseEntity.ok(taskService.findById(uuid));
    }
}


