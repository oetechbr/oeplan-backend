package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.controller.v1.interfaces.BaseController;
import br.tech.oe.plan.dto.group.GroupDTO;
import br.tech.oe.plan.service.GroupService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController implements BaseController<GroupDTO> {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public ResponseEntity<List<GroupDTO>> findAll(HttpSession session) {
        return ResponseEntity.ok(groupService.findAll());
    }

    @Override
    public ResponseEntity<GroupDTO> findById(UUID uuid) {
        return ResponseEntity.ok(groupService.findById(uuid));
    }
}


