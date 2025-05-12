package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.mappers.UserMapper;
import br.tech.oe.plan.dto.user.UserDTO;
import br.tech.oe.plan.dto.user.UserLoginDTO;
import br.tech.oe.plan.dto.user.UserRegisterDTO;
import br.tech.oe.plan.exception.ItemAlreadyExistException;
import br.tech.oe.plan.exception.NotAuthenticatedException;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
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

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserRegisterDTO user, HttpServletRequest request) {
        if (request.getSession(false) != null) {
            throw new ItemAlreadyExistException(
                    "An active session already exists. You need to log out first."
            );
        }

        UserDTO dto = authService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid UserLoginDTO userLogin,
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
        session.setAttribute("role", user.getRole().getRole().name());

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
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
}
