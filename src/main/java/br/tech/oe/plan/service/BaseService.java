package br.tech.oe.plan.service;

import java.util.List;
import java.util.UUID;

public interface BaseService<D, F> {
    List<D> findAll(F filters);

    D findById(UUID uuid);
}
