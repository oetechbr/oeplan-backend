package br.tech.oe.plan.service;

import br.tech.oe.plan.controller.v1.filters.GroupFilter;
import br.tech.oe.plan.dto.group.CreateGroupDTO;
import br.tech.oe.plan.dto.group.GroupDTO;
import br.tech.oe.plan.dto.group.UpdateGroupDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface GroupService extends BaseService<GroupDTO, GroupFilter> {
    GroupDTO save(CreateGroupDTO dto);

    @Transactional
    GroupDTO patch(UUID uuid, UpdateGroupDTO dto);

    @Transactional
    void delete(UUID uuid);
}