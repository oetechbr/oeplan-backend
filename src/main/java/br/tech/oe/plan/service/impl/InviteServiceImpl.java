package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.user.CreateUserInviteDTO;
import br.tech.oe.plan.dto.user.UserInviteDTO;
import br.tech.oe.plan.enums.InviteStatus;
import br.tech.oe.plan.enums.UserStatus;
import br.tech.oe.plan.exception.BadRequestException;
import br.tech.oe.plan.exception.ForbiddenException;
import br.tech.oe.plan.exception.ItemAlreadyExistException;
import br.tech.oe.plan.exception.ItemNotFoundException;
import br.tech.oe.plan.mapper.InviteMapper;
import br.tech.oe.plan.mapper.UserMapper;
import br.tech.oe.plan.model.UserInviteModel;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.repository.UserInviteRepository;
import br.tech.oe.plan.repository.UserRepository;
import br.tech.oe.plan.service.InviteService;
import br.tech.oe.plan.utils.Base64Utils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class InviteServiceImpl implements InviteService {

    private final UserRepository userRepository;

    private final UserInviteRepository userInviteRepository;

    public InviteServiceImpl(UserRepository userRepository, UserInviteRepository userInviteRepository) {
        this.userRepository = userRepository;
        this.userInviteRepository = userInviteRepository;
    }

    @Override
    public UserInviteDTO findUserInvite(UUID uuid) {
        var invite = userInviteRepository.findById(uuid).orElseThrow(
                () -> new ItemNotFoundException("Invite doesn't exist")
        );
        return InviteMapper.toDTO(invite);
    }

    @Override
    public UserInviteDTO saveUserInvite(CreateUserInviteDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ForbiddenException("Access denied");
        }
        var user = (UserModel) authentication.getPrincipal();

        if (userInviteRepository.existsByEmail(dto.getUser().getEmail())) {
            throw new ItemAlreadyExistException("User already invited");
        }

        var invitedUserModel = UserMapper.fromCreateDTO(dto.getUser());
        invitedUserModel.setStatus(UserStatus.INVITED);
        invitedUserModel.setPassword("");
        var invitedUser = userRepository.save(invitedUserModel);

        UserInviteModel model = new UserInviteModel();
        model.setEmail(dto.getUser().getEmail());
        model.setDescription(dto.getDescription());
        model.setInvitedBy(user);
        model.setInvitedUser(invitedUser);

        var userInvite = userInviteRepository.save(model);
        return InviteMapper.toDTO(userInvite);
    }

    @Override
    public void validateUserInvite(String token) {
        var uuid = Base64Utils.toUUID(token);

        var invite = userInviteRepository.findById(uuid).orElseThrow(
                () -> new BadRequestException("Invalid token")
        );

        if (invite.getAcceptedAt() != null) {
            updateStatusIfNeeded(invite, InviteStatus.ACCEPTED);
            throw new ForbiddenException("Invitation has already been accepted");
        }

        if (invite.getExpiresAt() != null && Instant.now().isAfter(invite.getExpiresAt())) {
            updateStatusIfNeeded(invite, InviteStatus.EXPIRED);
            throw new ForbiddenException("Invitation has expired");
        }

        if (invite.getStatus() == InviteStatus.REVOKED) {
            throw new ForbiddenException("This invitation has been revoked and is no longer valid");
        }

        invite.setAcceptedAt(Instant.now());
        invite.setStatus(InviteStatus.ACCEPTED);
        userInviteRepository.save(invite);

        var invitedUser = invite.getInvitedUser();
        invitedUser.setStatus(UserStatus.ACTIVE);
        userRepository.save(invitedUser);
    }

    @Override
    public void revokeUserInvite(String token) {
        var uuid = Base64Utils.toUUID(token);

        var invite = userInviteRepository.findById(uuid).orElseThrow(
                () -> new BadRequestException("Invalid token")
        );

        if (invite.getAcceptedAt() != null) {
            updateStatusIfNeeded(invite, InviteStatus.ACCEPTED);
            throw new ForbiddenException("Invitation has already been accepted");
        }

        if (invite.getExpiresAt() != null && Instant.now().isAfter(invite.getExpiresAt())) {
            updateStatusIfNeeded(invite, InviteStatus.EXPIRED);
            throw new ForbiddenException("Invitation has expired");
        }

        invite.setStatus(InviteStatus.REVOKED);
        userInviteRepository.save(invite);
    }

    private void updateStatusIfNeeded(UserInviteModel invite, InviteStatus status) {
        if (invite.getStatus() != status) {
            invite.setStatus(status);
            userInviteRepository.save(invite);
        }
    }
}
