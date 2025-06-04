package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.controller.v1.filters.TaskFilter;
import br.tech.oe.plan.dto.task.CreateTaskDTO;
import br.tech.oe.plan.dto.task.TaskDTO;
import br.tech.oe.plan.dto.task.UpdateTaskDTO;
import br.tech.oe.plan.exception.BadRequestException;
import br.tech.oe.plan.exception.InternalServerErrorException;
import br.tech.oe.plan.exception.ItemNotFoundException;
import br.tech.oe.plan.mapper.TaskMapper;
import br.tech.oe.plan.model.GroupModel;
import br.tech.oe.plan.model.TaskModel;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.repository.GroupRepository;
import br.tech.oe.plan.repository.TaskRepository;
import br.tech.oe.plan.repository.UserRepository;
import br.tech.oe.plan.security.utils.SecurityUtils;
import br.tech.oe.plan.service.TaskService;
import br.tech.oe.plan.service.utils.ServiceMatcher;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final ModelMapper modelMapper;

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    @Autowired
    public TaskServiceImpl(
            ModelMapper modelMapper,
            TaskRepository taskRepository,
            UserRepository userRepository,
            GroupRepository groupRepository
    ) {
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<TaskDTO> findAll(TaskFilter filters) {
        var user = SecurityUtils.getAuthenticatedOrThrow();

        var example = new TaskModel();
        var assignedBy = UserModel.withUUIDOrNull(filters.getAssignedBy());
        var group = GroupModel.withUUIDOrNull(filters.getGroupUuid());

        // Only allow tasks from current user
        if (filters.getGroupUuid() == null) {
            var assignedTo = UserModel.withUUIDOrNull(user.getUuid());
            example.setAssignedTo(assignedTo);
        }

        example.setGroup(group);
        example.setAssignedBy(assignedBy);
        modelMapper.map(filters, example);

        var matcher = Example.of(example, ServiceMatcher.DefaultMatcher);
        List<TaskModel> res = taskRepository.findAll(matcher);
        return TaskMapper.toDTO(res);
    }

    @Override
    public TaskDTO findById(UUID uuid) {
        var res = taskRepository.findById(uuid).orElseThrow(ItemNotFoundException::new);
        return TaskMapper.toDTO(res);
    }

    @Override
    public TaskDTO save(CreateTaskDTO task) {
        var user = SecurityUtils.getAuthenticatedOrThrow();

        var model = TaskMapper.fromDTO(task);
        model.setAssignedBy(user);

        if (task.getGroupUuid() != null) {
            var group = groupRepository.findById(task.getGroupUuid()).orElseThrow(
                    () -> new BadRequestException("Group doesn't exist")
            );

            model.setGroup(group);
        }

        var assignedToUser = userRepository.findById(task.getAssignedTo()).orElseThrow(
                () -> new ItemNotFoundException("User assigned to doesn't exist")
        );

        model.setAssignedTo(assignedToUser);
        var savedModel = taskRepository.save(model);

        return TaskMapper.toDTO(savedModel);
    }

    @Override
    public TaskDTO patch(UUID uuid, UpdateTaskDTO patch) {
        var task = taskRepository.findById(uuid).orElseThrow(
                () -> new ItemNotFoundException("Task not found")
        );

        modelMapper.map(patch, task);
        var updatedTask = taskRepository.save(task);
        return TaskMapper.toDTO(updatedTask);
    }

    @Override
    public void delete(UUID uuid) {
        taskRepository.findById(uuid).orElseThrow(
                () -> new ItemNotFoundException("Task doesn't exist")
        );

        if (taskRepository.deleteByUuid(uuid) == 0) {
            throw new InternalServerErrorException("Couldn't delete task");
        }
    }
}
