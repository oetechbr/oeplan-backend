package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.mappers.UserMapper;
import br.tech.oe.plan.dto.user.UserDTO;
import br.tech.oe.plan.dto.user.UserRegisterDTO;
import br.tech.oe.plan.exception.ItemAlreadyExistException;
import br.tech.oe.plan.exception.ItemNotFoundException;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.model.UserRoleModel;
import br.tech.oe.plan.repository.UserRepository;
import br.tech.oe.plan.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(
            UserRepository userRepository,
            UserRoleRepository userRoleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO save(UserRegisterDTO request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new ItemAlreadyExistException("Username already exist");
        }

        String encryptedPass = passwordEncoder.encode(request.getPassword());
        request.setPassword(encryptedPass);

        UserRoleModel role = userRoleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ItemNotFoundException("Invalid role"));

        UserModel newUser = UserMapper.fromRegisterDTO(request);
        newUser.setRole(role);

        var res = userRepository.save(newUser);
        return UserMapper.toDTO(res);
    }

    @Override
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
