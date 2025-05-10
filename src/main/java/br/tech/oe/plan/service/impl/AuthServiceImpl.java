package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.mappers.UserMapper;
import br.tech.oe.plan.dto.user.UserDTO;
import br.tech.oe.plan.dto.user.UserRegisterDTO;
import br.tech.oe.plan.exception.ItemAlreadyExistException;
import br.tech.oe.plan.exception.ItemNotFoundException;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.model.UserRoleModel;
import br.tech.oe.plan.model.UserStatusModel;
import br.tech.oe.plan.repository.UserRepository;
import br.tech.oe.plan.repository.UserRoleRepository;
import br.tech.oe.plan.repository.UserStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserStatusRepository userStatusRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(
            UserRepository userRepository,
            UserRoleRepository userRoleRepository,
            UserStatusRepository userStatusRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userStatusRepository = userStatusRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO save(UserRegisterDTO request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new ItemAlreadyExistException("Username already exist");
        }

        String encryptedPass = passwordEncoder.encode(request.getPassword());
        request.setPassword(encryptedPass);

        UserModel newUser = UserMapper.fromRegisterDTO(request);

        UserRoleModel role = userRoleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ItemNotFoundException("Invalid role"));
        newUser.setRole(role);

        UserStatusModel status = userStatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new ItemNotFoundException("Invalid status"));
        newUser.setStatus(status);

        var res = userRepository.save(newUser);
        return UserMapper.toDTO(res);
    }

    @Override
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
