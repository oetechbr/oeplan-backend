package br.tech.oe.plan.repository;

import br.tech.oe.plan.model.user.UserStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatusModel, Long> {
}
