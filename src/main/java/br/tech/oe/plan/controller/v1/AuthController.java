package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.user.LoginUserDTO;
import br.tech.oe.plan.dto.user.PatchUserDTO;
import br.tech.oe.plan.dto.user.RegisterUserDTO;
import br.tech.oe.plan.dto.user.UserDTO;
import br.tech.oe.plan.exception.ItemAlreadyExistException;
import br.tech.oe.plan.exception.NotAuthenticatedException;
import br.tech.oe.plan.mapper.UserMapper;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(
        name = "Auth",
        description = "Endpoints for managing auth"
)
public class AuthController {

    private final AuthServiceImpl authService;

    private final AuthenticationManager authenticationManager;

    private final HttpSessionSecurityContextRepository httpSessionSecurityContextRepository;

    @Autowired
    public AuthController(
            AuthServiceImpl authService,
            AuthenticationManager authenticationManager,
            HttpSessionSecurityContextRepository httpSessionSecurityContextRepository
    ) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.httpSessionSecurityContextRepository = httpSessionSecurityContextRepository;
    }

    @PostMapping(
            value = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Register user")
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterUserDTO user, HttpServletRequest request) {
        if (request.getSession(false) != null) {
            throw new ItemAlreadyExistException(
                    "An active session already exists. You need to log out first."
            );
        }

        UserDTO dto = authService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping(
            value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Authenticate user")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginUserDTO userLogin,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (request.getSession(false) != null) {
            throw new ItemAlreadyExistException(
                    "An active session already exists. Please log out first to create a new session."
            );
        }

        var authToken = new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        var securityContext = SecurityContextHolder.getContextHolderStrategy().createEmptyContext();
        securityContext.setAuthentication(authentication);
        httpSessionSecurityContextRepository.saveContext(securityContext, request, response);

        var user = (UserModel) authentication.getPrincipal();
        var dto = UserMapper.toDTO(user);

        // Generate new session
        var session = request.getSession(true);
        session.setAttribute("uuid", user.getUuid());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole().name());

        return ResponseEntity.ok(dto);
    }

    @PostMapping(
            value = "/logout",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Logout current user")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping(
            value = "/me",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get current user")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<UserDTO> me(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new NotAuthenticatedException("No active session found");
        }

        String username = (String) session.getAttribute("username");
        if (username == null) {
            session.invalidate();
            throw new NotAuthenticatedException("User not authenticated");
        }

        UserModel user = authService.loadUserByUsername(username);
        var dto = UserMapper.toDTO(user);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping(
            value = "/me",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Patch current user")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<UserDTO> patchMe(@RequestBody @Valid PatchUserDTO patch, HttpServletRequest request) {
        var dto = authService.patch(patch);

        var session = request.getSession(true);
        session.setAttribute("uuid", dto.getUuid());
        session.setAttribute("username", dto.getUsername());
        session.setAttribute("role", dto.getRole());

        return ResponseEntity.ok(dto);
    }
}
