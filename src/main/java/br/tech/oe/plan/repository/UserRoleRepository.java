package br.tech.oe.plan.repository;

import br.tech.oe.plan.model.user.UserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleModel, Long> {
}
