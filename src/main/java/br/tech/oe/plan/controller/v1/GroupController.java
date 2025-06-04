package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.group.CreateGroupDTO;
import br.tech.oe.plan.dto.group.GroupDTO;
import br.tech.oe.plan.dto.group.UpdateGroupDTO;
import br.tech.oe.plan.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups")
@Tag(name = "Groups", description = "Endpoints for managing groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all groups")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<List<GroupDTO>> findAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get single group")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<GroupDTO> findById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(groupService.findById(uuid));
    }

    @PostMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Save single group")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<GroupDTO> save(@RequestBody @Valid CreateGroupDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.save(dto));
    }

    @PatchMapping(
            value = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Patch single group")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<GroupDTO> patch(@PathVariable UUID uuid, @RequestBody @Valid UpdateGroupDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.patch(uuid, dto));
    }

    @DeleteMapping(value = "/{uuid}")
    @Operation(summary = "Delete single group")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        groupService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}


