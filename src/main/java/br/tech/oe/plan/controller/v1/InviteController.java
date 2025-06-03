package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.user.CreateUserInviteDTO;
import br.tech.oe.plan.dto.user.UserDTO;
import br.tech.oe.plan.dto.user.UserInviteDTO;
import br.tech.oe.plan.mapper.UserMapper;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.service.InviteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/invites")
public class InviteController {

    private final InviteService inviteService;

    private final HttpSessionSecurityContextRepository httpSessionSecurityContextRepository;

    public InviteController(
            InviteService inviteService,
            HttpSessionSecurityContextRepository httpSessionSecurityContextRepository
    ) {
        this.inviteService = inviteService;
        this.httpSessionSecurityContextRepository = httpSessionSecurityContextRepository;
    }

    @GetMapping("/users/{uuid}")
    public ResponseEntity<UserInviteDTO> findUserInvite(@PathVariable UUID uuid) {
        return ResponseEntity.ok(inviteService.findUserInvite(uuid));
    }

    @PostMapping("/users/invite")
    public ResponseEntity<UserInviteDTO> saveUserInvite(@RequestBody @Valid CreateUserInviteDTO dto) {
        return ResponseEntity.ok(inviteService.saveUserInvite(dto));
    }

    @PostMapping("/users/validate")
    public ResponseEntity<UserDTO> validateUserInvite(
            @RequestParam String token,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        var inviteAuth = inviteService.validateUserInvite(token);

        var securityContext = SecurityContextHolder.getContextHolderStrategy().createEmptyContext();
        securityContext.setAuthentication(inviteAuth);
        httpSessionSecurityContextRepository.saveContext(securityContext, request, response);

        var user = (UserModel) inviteAuth.getPrincipal();
        var dto = UserMapper.toDTO(user);

        // Generate new session
        var session = request.getSession(true);
        session.setAttribute("uuid", user.getUuid());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole().name());

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/users/revoke")
    public ResponseEntity<UserInviteDTO> revokeUserInvite(@RequestParam String token) {
        inviteService.revokeUserInvite(token);
        return ResponseEntity.ok().build();
    }
}
