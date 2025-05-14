package br.tech.oe.plan.service;

import br.tech.oe.plan.dto.task.CreateTaskDTO;
import br.tech.oe.plan.dto.task.TaskDTO;
import br.tech.oe.plan.dto.task.UpdateTaskDTO;

import java.util.UUID;

public interface TaskService extends BaseService<TaskDTO> {
    TaskDTO save(CreateTaskDTO task);

    TaskDTO patch(UUID uuid, UpdateTaskDTO task);
}