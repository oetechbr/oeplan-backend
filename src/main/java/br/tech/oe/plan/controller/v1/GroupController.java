package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.group.CreateGroupDTO;
import br.tech.oe.plan.dto.group.GroupDTO;
import br.tech.oe.plan.dto.group.UpdateGroupDTO;
import br.tech.oe.plan.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<GroupDTO> findById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(groupService.findById(uuid));
    }

    @PostMapping
    public ResponseEntity<GroupDTO> save(@RequestBody @Valid CreateGroupDTO task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.save(task));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<GroupDTO> save(@PathVariable UUID uuid, @RequestBody @Valid UpdateGroupDTO task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.patch(uuid, task));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        groupService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}


