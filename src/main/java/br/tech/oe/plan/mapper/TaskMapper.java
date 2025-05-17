package br.tech.oe.plan.mapper;

import br.tech.oe.plan.dto.task.CreateTaskDTO;
import br.tech.oe.plan.dto.task.TaskDTO;
import br.tech.oe.plan.dto.task.UserTaskDTO;
import br.tech.oe.plan.model.TaskModel;
import br.tech.oe.plan.model.UserModel;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {
    public static TaskDTO toDTO(TaskModel model) {
        TaskDTO dto = new TaskDTO();
        dto.setUuid(model.getUuid());
        dto.setTitle(model.getTitle());
        dto.setDescription(model.getDescription());
        dto.setDueDate(model.getDueDate());
        dto.setAssignedTo(toUserTaskDTO(model.getAssignedTo()));
        dto.setAssignedBy(toUserTaskDTO(model.getAssignedBy()));
        dto.setStatus(model.getStatus().name());
        dto.setPriority(model.getPriority().name());
        dto.setVisibility(model.getVisibility().name());
        dto.setTags(model.getTags());
        dto.setArchivedAt(model.getArchivedAt());
        dto.setCompletedAt(model.getCompletedAt());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        return dto;
    }

    public static List<TaskDTO> toDTO(List<TaskModel> models) {
        return models.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static UserTaskDTO toUserTaskDTO(UserModel model) {
        UserTaskDTO dto = new UserTaskDTO();
        dto.setUuid(model.getUuid());
        dto.setUsername(model.getUsername());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setEmail(model.getEmail());
        dto.setAvatarUrl(model.getAvatarUrl());
        return dto;
    }

    public static TaskModel fromDTO(CreateTaskDTO dto) {
        TaskModel model = new TaskModel();
        model.setTitle(dto.getTitle());
        model.setDescription(dto.getDescription());
        model.setDueDate(dto.getDueDate());
        model.setStatus(dto.getStatus());
        model.setPriority(dto.getPriority());
        model.setVisibility(dto.getVisibility());
        model.setTags(dto.getTags());
        return model;
    }
}
