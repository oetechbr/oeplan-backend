package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.GroupDTO;
import br.tech.oe.plan.dto.mappers.GroupMapper;
import br.tech.oe.plan.exception.ItemNotFoundException;
import br.tech.oe.plan.model.GroupModel;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.repository.GroupRepository;
import br.tech.oe.plan.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
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
    public GroupDTO findById(UUID uuid) throws ItemNotFoundException {
        GroupModel res = groupRepository.findById(uuid).orElseThrow(ItemNotFoundException::new);
        return GroupMapper.toDTO(res);
    }
}
