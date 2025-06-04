package br.tech.oe.plan.service;

import br.tech.oe.plan.controller.v1.filters.TaskFilter;
import br.tech.oe.plan.dto.task.CreateTaskDTO;
import br.tech.oe.plan.dto.task.TaskDTO;
import br.tech.oe.plan.dto.task.UpdateTaskDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface TaskService extends BaseService<TaskDTO, TaskFilter> {
    TaskDTO save(CreateTaskDTO task);

    @Transactional
    TaskDTO patch(UUID uuid, UpdateTaskDTO task);

    @Transactional
    void delete(UUID uuid);
}