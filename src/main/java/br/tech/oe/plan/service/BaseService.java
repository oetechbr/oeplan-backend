package br.tech.oe.plan.service;

import br.tech.oe.plan.exception.ItemNotFoundException;

import java.util.List;
import java.util.UUID;

public interface BaseService<D> {
    List<D> findAll();

    D findById(UUID uuid) throws ItemNotFoundException;
}
