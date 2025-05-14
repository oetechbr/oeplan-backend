package br.tech.oe.plan.mapper;

import br.tech.oe.plan.dto.group.CreateGroupDTO;
import br.tech.oe.plan.dto.group.GroupDTO;
import br.tech.oe.plan.model.GroupModel;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public static GroupDTO toDTO(GroupModel model) {
        GroupDTO dto = new GroupDTO();
        dto.setUuid(model.getUuid());
        dto.setOwnerUuid(model.getOwner().getUuid());
        dto.setTitle(model.getTitle());
        dto.setDescription(model.getDescription());
        dto.setCode(model.getCode());
        dto.setTags(model.getTags());
        dto.setColor(model.getColor());
        dto.setCategory(model.getCategory());
        dto.setVisibility(model.getVisibility());
        dto.setArchivedAt(model.getArchivedAt());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        return dto;
    }

    public static List<GroupDTO> toDTO(List<GroupModel> models) {
        return models.stream()
                .map(GroupMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static GroupModel fromDTO(CreateGroupDTO dto) {
        GroupModel model = new GroupModel();
        model.setTitle(dto.getTitle());
        model.setDescription(dto.getDescription());
        model.setCode(dto.getCode());
        model.setTags(dto.getTags());
        model.setColor(dto.getColor());
        model.setCategory(dto.getCategory());
        model.setVisibility(dto.getVisibility());
        model.setArchivedAt(dto.getArchivedAt());
        return model;
    }
}
