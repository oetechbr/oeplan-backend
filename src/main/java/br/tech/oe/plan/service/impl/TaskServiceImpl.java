package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.TaskDTO;
import br.tech.oe.plan.exception.ItemNotFoundException;
import br.tech.oe.plan.model.TaskModel;
import br.tech.oe.plan.repository.TaskRepository;
import br.tech.oe.plan.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TaskDTO> findAll() {
        List<TaskModel> res = taskRepository.findAll();
        return Arrays.asList(modelMapper.map(res, TaskDTO[].class));
    }

    @Override
    public TaskDTO findById(UUID uuid) throws ItemNotFoundException {
        var res = taskRepository.findById(uuid).orElseThrow(ItemNotFoundException::new);
        return modelMapper.map(res, TaskDTO.class);
    }
}
