package br.tech.oe.plan.service;

import br.tech.oe.plan.dto.InviteMailRequest;

public interface MailService {
    void sendUserInvite(InviteMailRequest invite);
}
