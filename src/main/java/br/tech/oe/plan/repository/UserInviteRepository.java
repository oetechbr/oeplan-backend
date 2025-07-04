package br.tech.oe.plan.repository;

import br.tech.oe.plan.model.UserInviteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserInviteRepository extends JpaRepository<UserInviteModel, UUID> {
    boolean existsByEmail(String email);
}
