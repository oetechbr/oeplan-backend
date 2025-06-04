package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.attachment.AttachmentDTO;
import br.tech.oe.plan.dto.attachment.CreateAttachmentDTO;
import br.tech.oe.plan.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/attachments")
@Tag(name = "Attachments", description = "Endpoints for managing attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping(
            value = "/upload",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Save single file")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<AttachmentDTO> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "fileName", required = false) String fileName,
            @RequestParam(value = "description", required = false) String description
    ) {
        var dto = new CreateAttachmentDTO();
        dto.setFileName(fileName);
        dto.setDescription(description);
        return ResponseEntity.ok().body(attachmentService.uploadFile(file, dto));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "Delete single file")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> deleteFile(@RequestParam UUID uuid) {
        attachmentService.deleteFile(uuid);
        return ResponseEntity.noContent().build();
    }
}
