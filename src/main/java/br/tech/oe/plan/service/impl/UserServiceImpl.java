package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.mappers.UserMapper;
import br.tech.oe.plan.dto.user.UserDTO;
import br.tech.oe.plan.exception.ItemNotFoundException;
import br.tech.oe.plan.repository.UserRepository;
import br.tech.oe.plan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> findAll() {
        return UserMapper.toDTO(userRepository.findAll());
    }

    @Override
    public UserDTO findById(UUID uuid) throws ItemNotFoundException {
        var res = userRepository.findById(uuid).orElseThrow(ItemNotFoundException::new);
        return UserMapper.toDTO(res);
    }
}
