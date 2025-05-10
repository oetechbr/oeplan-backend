package br.tech.oe.plan.controller.v1.interfaces;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface BaseController<D> {
    @GetMapping
    ResponseEntity<List<D>> findAll(HttpSession session);

    @GetMapping("/{uuid}")
    ResponseEntity<D> findById(@PathVariable UUID uuid);
}
