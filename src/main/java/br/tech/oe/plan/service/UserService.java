package br.tech.oe.plan.service;

import br.tech.oe.plan.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
}