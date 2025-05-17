package br.tech.oe.plan.service.impl;

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
import br.tech.oe.plan.service.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<GroupDTO> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (UserModel) authentication.getPrincipal();

        List<GroupModel> res = groupRepository.findAllByOwnerUuid(user.getUuid());
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
