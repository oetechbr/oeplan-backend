package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.user.CreateUserInviteDTO;
import br.tech.oe.plan.dto.user.UserInviteDTO;
import br.tech.oe.plan.service.InviteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/invites")
public class InviteController {

    private final InviteService inviteService;

    public InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @GetMapping("/users/{uuid}")
    public ResponseEntity<UserInviteDTO> findUserInvite(@PathVariable UUID uuid) {
        return ResponseEntity.ok(inviteService.findUserInvite(uuid));
    }

    @PostMapping("/users/generate")
    public ResponseEntity<UserInviteDTO> saveUserInvite(@RequestBody CreateUserInviteDTO dto) {
        return ResponseEntity.ok(inviteService.saveUserInvite(dto));
    }

    @PostMapping("/users/validate")
    public ResponseEntity<UserInviteDTO> validateUserInvite(@RequestParam String token) {
        inviteService.validateUserInvite(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/revoke")
    public ResponseEntity<UserInviteDTO> revokeUserInvite(@RequestParam String token) {
        inviteService.revokeUserInvite(token);
        return ResponseEntity.ok().build();
    }
}
