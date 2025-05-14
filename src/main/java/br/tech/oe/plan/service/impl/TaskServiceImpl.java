package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.task.CreateTaskDTO;
import br.tech.oe.plan.dto.task.TaskDTO;
import br.tech.oe.plan.dto.task.UpdateTaskDTO;
import br.tech.oe.plan.exception.BadRequestException;
import br.tech.oe.plan.exception.ItemNotFoundException;
import br.tech.oe.plan.mapper.TaskMapper;
import br.tech.oe.plan.model.task.TaskModel;
import br.tech.oe.plan.model.user.UserModel;
import br.tech.oe.plan.repository.GroupRepository;
import br.tech.oe.plan.repository.TaskRepository;
import br.tech.oe.plan.repository.UserRepository;
import br.tech.oe.plan.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<TaskDTO> findAll() {
        List<TaskModel> res = taskRepository.findAll();
        return TaskMapper.toDTO(res);
    }

    @Override
    public TaskDTO findById(UUID uuid) throws ItemNotFoundException {
        var res = taskRepository.findById(uuid).orElseThrow(ItemNotFoundException::new);
        return TaskMapper.toDTO(res);
    }

    @Override
    public TaskDTO save(CreateTaskDTO task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (UserModel) authentication.getPrincipal();

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
}
