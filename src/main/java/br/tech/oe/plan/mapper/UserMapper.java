package br.tech.oe.plan.mapper;

import br.tech.oe.plan.dto.SimpleUserDTO;
import br.tech.oe.plan.dto.user.CreateUserDTO;
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

    public static UserModel fromRegisterDTO(RegisterUserDTO dto) {
        UserModel model = new UserModel();
        model.setUsername(dto.getUsername());
        model.setFirstName(dto.getFirstName());
        model.setLastName(dto.getLastName());
        model.setPassword(dto.getPassword());
        model.setEmail(dto.getEmail());
        model.setRole(dto.getRole());
        model.setStatus(dto.getStatus());
        model.setAvatarUrl(dto.getAvatarUrl());
        model.setPhone(dto.getPhone());
        model.setGender(dto.getGender());
        model.setBirthDate(dto.getBirthDate());
        model.setDepartment(dto.getDepartment());
        model.setTitlePosition(dto.getTitlePosition());
        model.setEmailVerified(dto.isEmailVerified());
        return model;
    }

    public static UserModel fromCreateDTO(CreateUserDTO dto) {
        UserModel model = new UserModel();
        model.setUsername(dto.getUsername());
        model.setFirstName(dto.getFirstName());
        model.setLastName(dto.getLastName());
        model.setEmail(dto.getEmail());
        model.setRole(dto.getRole());
        return model;
    }

    public static SimpleUserDTO toSimpleUserDTO(UserModel model) {
        SimpleUserDTO dto = new SimpleUserDTO();
        dto.setUuid(model.getUuid());
        dto.setUsername(model.getUsername());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setEmail(model.getEmail());
        dto.setAvatarUrl(model.getAvatarUrl());
        return dto;
    }
}
