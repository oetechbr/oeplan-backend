package br.tech.oe.plan.repository;

import br.tech.oe.plan.model.GroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupModel, UUID> {
    Long deleteByUuid(UUID uuid);
}
