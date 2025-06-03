package br.tech.oe.plan.service;

import br.tech.oe.plan.dto.user.CreateUserInviteDTO;
import br.tech.oe.plan.dto.user.UserInviteDTO;
import br.tech.oe.plan.security.authentication.InviteAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface InviteService {
    UserInviteDTO findUserInvite(UUID uuid);

    @Transactional
    UserInviteDTO saveUserInvite(CreateUserInviteDTO dto);

    @Transactional
    InviteAuthenticationToken validateUserInvite(String token);

    @Transactional
    void revokeUserInvite(String token);
}
