package br.tech.oe.plan.repository;

import br.tech.oe.plan.model.TaskModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {

    @Override
    @EntityGraph(attributePaths = {"status", "priority", "visibility", "assignedBy", "assignedTo"})
    List<TaskModel> findAll();
}
