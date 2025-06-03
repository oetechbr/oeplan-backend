package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.user.PatchUserDTO;
import br.tech.oe.plan.dto.user.RegisterUserDTO;
import br.tech.oe.plan.dto.user.UserDTO;
import br.tech.oe.plan.exception.ItemAlreadyExistException;
import br.tech.oe.plan.mapper.UserMapper;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.repository.UserRepository;
import br.tech.oe.plan.security.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements UserDetailsService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO save(RegisterUserDTO request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new ItemAlreadyExistException("Username already exist");
        }

        String encryptedPass = passwordEncoder.encode(request.getPassword());
        request.setPassword(encryptedPass);

        UserModel newUser = UserMapper.fromRegisterDTO(request);
        var res = userRepository.save(newUser);
        return UserMapper.toDTO(res);
    }

    public UserDTO patch(PatchUserDTO patch) {
        var user = SecurityUtils.getAuthenticatedOrThrow();

        modelMapper.map(patch, user);

        // Email was changed
        if (patch.getEmail() != null) user.setEmailVerified(false);

        var res = userRepository.save(user);
        return UserMapper.toDTO(res);
    }

    @Override
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
