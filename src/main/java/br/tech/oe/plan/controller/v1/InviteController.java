package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.user.CreateUserInviteDTO;
import br.tech.oe.plan.dto.user.UserDTO;
import br.tech.oe.plan.dto.user.UserInviteDTO;
import br.tech.oe.plan.mapper.UserMapper;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.service.InviteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/invites")
@Tag(
        name = "Invites",
        description = "Endpoints for managing invites"
)
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

    @GetMapping(value = "/users/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get single user invite")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<UserInviteDTO> findUserInvite(@PathVariable UUID uuid) {
        return ResponseEntity.ok(inviteService.findUserInvite(uuid));
    }

    @PostMapping(
            value = "/users/invite",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Save single user invite")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<UserInviteDTO> saveUserInvite(@RequestBody @Valid CreateUserInviteDTO dto) {
        return ResponseEntity.ok(inviteService.saveUserInvite(dto));
    }

    @PostMapping(
            value = "/users/validate",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Validate user invite")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true)))
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

    @PostMapping(
            value = "/users/revoke",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Revoke user invite")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<UserInviteDTO> revokeUserInvite(@RequestParam String token) {
        inviteService.revokeUserInvite(token);
        return ResponseEntity.ok().build();
    }
}
