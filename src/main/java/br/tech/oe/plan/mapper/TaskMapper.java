package br.tech.oe.plan.mapper;

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
        dto.setStatusId(model.getStatus().getStatus().name());
        dto.setPriorityId(model.getPriority().getPriority().name());
        dto.setVisibilityId(model.getVisibility().getVisibility().name());
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
        dto.setProfilePicture(model.getProfilePicture());
        return dto;
    }
}
