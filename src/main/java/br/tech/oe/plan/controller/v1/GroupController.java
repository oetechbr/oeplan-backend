package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.group.GroupDTO;
import br.tech.oe.plan.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> findAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<GroupDTO> findById(UUID uuid) {
        return ResponseEntity.ok(groupService.findById(uuid));
    }
}


