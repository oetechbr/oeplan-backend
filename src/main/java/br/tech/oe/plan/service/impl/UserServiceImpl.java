package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.UserDTO;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.repository.UserRepository;
import br.tech.oe.plan.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserModel> res = userRepository.findAll();
        return Arrays.asList(modelMapper.map(res, UserDTO[].class));
    }
}
