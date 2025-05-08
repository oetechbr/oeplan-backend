package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.controller.v1.interfaces.BaseController;
import br.tech.oe.plan.dto.UserDTO;
import br.tech.oe.plan.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements BaseController<UserDTO> {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.findById(uuid));
    }
}


