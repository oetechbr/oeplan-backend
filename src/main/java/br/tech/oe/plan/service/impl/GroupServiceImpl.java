package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.controller.v1.filters.GroupFilter;
import br.tech.oe.plan.dto.group.CreateGroupDTO;
import br.tech.oe.plan.dto.group.GroupDTO;
import br.tech.oe.plan.dto.group.UpdateGroupDTO;
import br.tech.oe.plan.exception.InternalServerErrorException;
import br.tech.oe.plan.exception.ItemNotFoundException;
import br.tech.oe.plan.mapper.GroupMapper;
import br.tech.oe.plan.model.GroupModel;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.repository.GroupRepository;
import br.tech.oe.plan.repository.UserRepository;
import br.tech.oe.plan.security.utils.SecurityUtils;
import br.tech.oe.plan.service.GroupService;
import br.tech.oe.plan.service.utils.ServiceMatcher;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(ModelMapper modelMapper, UserRepository userRepository, GroupRepository groupRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<GroupDTO> findAll(GroupFilter filters) {
        var user = SecurityUtils.getAuthenticatedOrThrow();

        // Cannot be null
        var owner = new UserModel();
        owner.setUuid(user.getUuid());

        var example = new GroupModel();
        example.setOwner(owner);
        modelMapper.map(filters, example);

        var matcher = Example.of(example, ServiceMatcher.DefaultMatcher);
        List<GroupModel> res = groupRepository.findAll(matcher);
        return GroupMapper.toDTO(res);
    }

    @Override
    public GroupDTO findById(UUID uuid) {
        GroupModel res = groupRepository.findById(uuid).orElseThrow(ItemNotFoundException::new);
        return GroupMapper.toDTO(res);
    }

    @Override
    public GroupDTO save(CreateGroupDTO dto) {
        var owner = userRepository.findById(dto.getOwnerUuid()).orElseThrow(
                () -> new ItemNotFoundException("User doesn't exist")
        );

        var model = GroupMapper.fromDTO(dto);
        model.setOwner(owner);

        var savedModel = groupRepository.save(model);
        return GroupMapper.toDTO(savedModel);
    }

    @Override
    public GroupDTO patch(UUID uuid, UpdateGroupDTO dto) {
        var res = groupRepository.findById(uuid).orElseThrow(
                () -> new ItemNotFoundException("Task not found")
        );

        modelMapper.map(dto, res);
        var updatedItem = groupRepository.save(res);
        return GroupMapper.toDTO(updatedItem);
    }

    @Override
    public void delete(UUID uuid) {
        groupRepository.findById(uuid).orElseThrow(
                () -> new ItemNotFoundException("Task doesn't exist")
        );

        if (groupRepository.deleteByUuid(uuid) == 0) {
            throw new InternalServerErrorException("Couldn't delete task");
        }
    }
}
