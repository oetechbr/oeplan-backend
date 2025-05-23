package br.tech.oe.plan.mapper;

import br.tech.oe.plan.dto.user.RegisterUserDTO;
import br.tech.oe.plan.dto.user.UserDTO;
import br.tech.oe.plan.model.UserModel;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO toDTO(UserModel model) {
        UserDTO dto = new UserDTO();
        dto.setUuid(model.getUuid());
        dto.setUsername(model.getUsername());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setEmail(model.getEmail());
        dto.setRole(model.getRole().name());
        dto.setStatus(model.getStatus().name());
        dto.setAvatarUrl(model.getAvatarUrl());
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

    public static UserModel fromRegisterDTO(RegisterUserDTO registerDTO) {
        UserModel model = new UserModel();
        model.setUsername(registerDTO.getUsername());
        model.setFirstName(registerDTO.getFirstName());
        model.setLastName(registerDTO.getLastName());
        model.setPassword(registerDTO.getPassword());
        model.setEmail(registerDTO.getEmail());
        model.setRole(registerDTO.getRole());
        model.setStatus(registerDTO.getStatus());
        model.setAvatarUrl(registerDTO.getAvatarUrl());
        model.setPhone(registerDTO.getPhone());
        model.setGender(registerDTO.getGender());
        model.setBirthDate(registerDTO.getBirthDate());
        model.setDepartment(registerDTO.getDepartment());
        model.setTitlePosition(registerDTO.getTitlePosition());
        model.setEmailVerified(registerDTO.isEmailVerified());
        return model;
    }
}
