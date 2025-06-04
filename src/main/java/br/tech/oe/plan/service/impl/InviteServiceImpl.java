package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.InviteMailRequest;
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
import br.tech.oe.plan.repository.UserInviteRepository;
import br.tech.oe.plan.repository.UserRepository;
import br.tech.oe.plan.security.authentication.InviteAuthenticationToken;
import br.tech.oe.plan.security.utils.SecurityUtils;
import br.tech.oe.plan.service.InviteService;
import br.tech.oe.plan.service.MailService;
import br.tech.oe.plan.utils.Base64Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Service
public class InviteServiceImpl implements InviteService {

    @Value("${br.tech.oe.plan.application.webapp.invite-url}")
    private String inviteUrl;

    private final MessageSource messageSource;

    private final MailService mailService;

    private final UserRepository userRepository;

    private final UserInviteRepository userInviteRepository;

    public InviteServiceImpl(
            MessageSource messageSource,
            MailService mailService,
            UserRepository userRepository,
            UserInviteRepository userInviteRepository
    ) {
        this.messageSource = messageSource;
        this.mailService = mailService;
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
        var user = SecurityUtils.getAuthenticatedOrThrow();

        if (userInviteRepository.existsByEmail(dto.getUser().getEmail())) {
            throw new ItemAlreadyExistException("User already invited");
        }

        var invitedUserModel = UserMapper.fromCreateDTO(dto.getUser());
        invitedUserModel.setStatus(UserStatus.INVITED);
        invitedUserModel.setPassword("");
        var invitedUser = userRepository.save(invitedUserModel);

        UserInviteModel model = new UserInviteModel();
        model.setEmail(dto.getUser().getEmail());
        model.setInvitedBy(user);
        model.setInvitedUser(invitedUser);
        var userInvite = userInviteRepository.save(model);

        // Mail
        String inviteMessage = messageSource.getMessage(
                "invite.message",
                new String[]{user.getFirstName()},
                new Locale("pt", "BR")
        );
        var token = Base64Utils.uuidToBase64(model.getUuid());
        var inviteMail = new InviteMailRequest();
        inviteMail.setTitle(inviteMessage);
        inviteMail.setTo(model.getEmail());
        inviteMail.setInviteUrl(inviteUrl + "?token=" + token);
        inviteMail.setUser(user.getFirstName());
        mailService.sendUserInvite(inviteMail);

        return InviteMapper.toDTO(userInvite);
    }

    @Override
    public InviteAuthenticationToken validateUserInvite(String token) {
        var uuid = Base64Utils.toUUID(token);

        var invite = userInviteRepository.findById(uuid).orElseThrow(
                () -> new BadRequestException("Invalid token")
        );

        // The access doesn't expire
        if (invite.getAcceptedAt() != null) {
            updateStatusIfNeeded(invite, InviteStatus.ACCEPTED);
            return new InviteAuthenticationToken(invite.getInvitedUser());
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
        invitedUser.setStatus(UserStatus.INCOMPLETE);
        invitedUser.setEmailVerified(true);
        var savedInvitedUser = userRepository.save(invitedUser);

        return new InviteAuthenticationToken(savedInvitedUser);
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
