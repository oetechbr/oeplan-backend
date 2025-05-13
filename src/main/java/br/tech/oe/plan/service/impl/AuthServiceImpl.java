package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.user.CreateUserInviteDTO;
import br.tech.oe.plan.dto.user.RegisterUserDTO;
import br.tech.oe.plan.dto.user.UserDTO;
import br.tech.oe.plan.enums.UserStatus;
import br.tech.oe.plan.exception.BadRequestException;
import br.tech.oe.plan.exception.ForbiddenException;
import br.tech.oe.plan.exception.ItemAlreadyExistException;
import br.tech.oe.plan.exception.ItemNotFoundException;
import br.tech.oe.plan.mapper.UserMapper;
import br.tech.oe.plan.model.UserInviteModel;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.model.UserRoleModel;
import br.tech.oe.plan.model.UserStatusModel;
import br.tech.oe.plan.repository.UserInviteRepository;
import br.tech.oe.plan.repository.UserRepository;
import br.tech.oe.plan.repository.UserRoleRepository;
import br.tech.oe.plan.repository.UserStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserStatusRepository userStatusRepository;

    private final UserInviteRepository userInviteRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(
            UserRepository userRepository,
            UserRoleRepository userRoleRepository,
            UserStatusRepository userStatusRepository, UserInviteRepository userInviteRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userStatusRepository = userStatusRepository;
        this.userInviteRepository = userInviteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO save(RegisterUserDTO request) {
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

    public void saveUserInvite(CreateUserInviteDTO userInvite) {
        if (userInviteRepository.existsByEmail(userInvite.getEmail())) {
            throw new ItemAlreadyExistException("Email already invited");
        }

        var opInvitedUser = userRepository.findByEmail(userInvite.getEmail());
        if (opInvitedUser.isEmpty()) throw new ItemNotFoundException("User doesn't exist");
        var invitedUser = opInvitedUser.get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ForbiddenException("Access denied");
        }
        var user = (UserModel) authentication.getPrincipal();

        UserModel invitedByUser = new UserModel();
        invitedByUser.setUuid(user.getUuid());

        UserInviteModel model = new UserInviteModel();
        model.setEmail(userInvite.getEmail());
        model.setDescription(userInvite.getDescription());
        model.setToken(UUID.randomUUID().toString());
        model.setInvitedBy(invitedByUser);
        model.setUser(invitedUser);

        userInviteRepository.save(model);
    }

    @Transactional
    public void validateUserInvite(String token) {
        var opInvite = userInviteRepository.findByToken(token);
        if (opInvite.isEmpty()) throw new BadRequestException("Invalid token");

        var invite = opInvite.get();
        if (invite.getAcceptedAt() != null) {
            throw new ForbiddenException("Invitation has already been accepted");
        }

        var opInvitedUser = userRepository.findById(invite.getUser().getUuid());
        if (opInvitedUser.isEmpty()) throw new ItemNotFoundException("User doesn't exist");
        var invitedUser = opInvitedUser.get();

        invite.setAcceptedAt(Instant.now());
        userInviteRepository.save(invite);

        var status = new UserStatusModel();
        status.setId((long) UserStatus.ACTIVE.getCode());
        invitedUser.setStatus(status);
        userRepository.save(invitedUser);
    }
}
