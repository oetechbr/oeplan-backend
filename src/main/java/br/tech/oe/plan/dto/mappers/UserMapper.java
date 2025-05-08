package br.tech.oe.plan.dto.mappers;

import br.tech.oe.plan.dto.UserDTO;
import br.tech.oe.plan.model.UserModel;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO toDTO(UserModel model) {
        UserDTO dto = new UserDTO();
        dto.setUuid(model.getUuid());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setEmail(model.getEmail());
        dto.setRole(model.getRole().getRole().name());
        dto.setStatusId(model.getStatusId());
        dto.setProfilePicture(model.getProfilePicture());
        dto.setPhone(model.getPhone());
        dto.setGender(model.getGender());
        dto.setBirthDate(model.getBirthDate());
        dto.setDepartment(model.getDepartment());
        dto.setTitlePosition(model.getTitlePosition());
        dto.setEmailVerified(model.isEmailVerified());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        return dto;
    }

    public static List<UserDTO> toDTO(List<UserModel> models) {
        return models.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}
