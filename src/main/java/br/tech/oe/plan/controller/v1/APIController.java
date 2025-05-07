package br.tech.oe.plan.controller.v1;

import br.tech.oe.plan.dto.APIInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class APIController {

    private final APIInfoDTO apiInfo;

    @Autowired
    public APIController(APIInfoDTO apiInfo) {
        this.apiInfo = apiInfo;
    }

    @GetMapping
    public ResponseEntity<APIInfoDTO> getAPIInfo() {
        return ResponseEntity.ok(apiInfo);
    }

    @GetMapping("/ping")
    public ResponseEntity<String> getPong() {
        return ResponseEntity.ok("Pong");
    }
}


