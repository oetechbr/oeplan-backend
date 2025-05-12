package br.tech.oe.plan.repository;

import br.tech.oe.plan.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, UUID> {
    List<CommentModel> findAllByTaskUuid(UUID taskUuid);

    Optional<CommentModel> findByUuidAndTaskUuid(UUID uuid, UUID taskUuid);

    Long deleteByUuidAndUserUuid(UUID uuid, UUID userUuid);
}
