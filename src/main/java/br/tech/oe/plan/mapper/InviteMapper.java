package br.tech.oe.plan.mapper;

import br.tech.oe.plan.dto.user.UserInviteDTO;
import br.tech.oe.plan.model.UserInviteModel;
import br.tech.oe.plan.utils.Base64Utils;

public class InviteMapper {

    public static UserInviteDTO toDTO(UserInviteModel model) {
        var dto = new UserInviteDTO();
        var token = Base64Utils.uuidToBase64(model.getUuid());
        dto.setToken(token);
        dto.setInvitedBy(UserMapper.toSimpleUserDTO(model.getInvitedBy()));
        dto.setInvitedUser(UserMapper.toSimpleUserDTO(model.getInvitedUser()));
        dto.setStatus(model.getStatus());
        dto.setAcceptedAt(model.getAcceptedAt());
        dto.setExpiresAt(model.getExpiresAt());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        return dto;
    }
}
