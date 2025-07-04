package br.tech.oe.plan.repository;

import br.tech.oe.plan.model.AttachmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentModel, UUID> {
}
