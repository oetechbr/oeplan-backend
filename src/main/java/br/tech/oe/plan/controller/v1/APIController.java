package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.APIInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@Tag(name = "API", description = "Endpoints for managing API")
public class APIController {

    private final APIInfoDTO apiInfo;

    @Autowired
    public APIController(APIInfoDTO apiInfo) {
        this.apiInfo = apiInfo;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get API information")
    @ApiResponse(responseCode = "200", description = "Successful")
    public ResponseEntity<APIInfoDTO> getAPIInfo() {
        return ResponseEntity.ok(apiInfo);
    }

    @GetMapping(value = "/ping", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Ping API")
    @ApiResponse(responseCode = "200", description = "Successful")
    public ResponseEntity<String> getPong() {
        return ResponseEntity.ok("Pong");
    }
}


