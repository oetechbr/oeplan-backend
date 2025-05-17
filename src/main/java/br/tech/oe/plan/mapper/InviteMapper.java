package br.tech.oe.plan.mapper;

import br.tech.oe.plan.dto.SimpleUserDTO;
import br.tech.oe.plan.dto.user.UserInviteDTO;
import br.tech.oe.plan.model.UserInviteModel;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.utils.Base64Utils;

public class InviteMapper {

    private static SimpleUserDTO toInviteUserDTO(UserModel model) {
        SimpleUserDTO dto = new SimpleUserDTO();
        dto.setUuid(model.getUuid());
        dto.setUsername(model.getUsername());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setEmail(model.getEmail());
        dto.setAvatarUrl(model.getAvatarUrl());
        return dto;
    }

    public static UserInviteDTO toDTO(UserInviteModel model) {
        var dto = new UserInviteDTO();
        var token = Base64Utils.uuidToBase64(model.getUuid());
        dto.setToken(token);
        dto.setDescription(model.getDescription());
        dto.setInvitedBy(toInviteUserDTO(model.getInvitedBy()));
        dto.setInvitedUser(toInviteUserDTO(model.getInvitedUser()));
        dto.setStatus(model.getStatus());
        dto.setAcceptedAt(model.getAcceptedAt());
        dto.setExpiresAt(model.getExpiresAt());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        return dto;
    }
}
