package br.tech.oe.plan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {

    @GetMapping("/ping")
    public ResponseEntity<String> getPong() {
        return ResponseEntity.ok("Pong");
    }
}


