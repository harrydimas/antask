package com.antask.node;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NodeRepository extends JpaRepository<Node, UUID> {
    @Query("FROM Node WHERE name = :name AND flow.id = :flowId ORDER BY dateCreated DESC")
    Node findByNameAndFlow(String name, UUID flowId);

    @Query("SELECT approvedNode FROM Node WHERE name = 'START' AND flow.id = :flowId ORDER BY dateCreated DESC")
    String findStartByFlow(UUID flowId);
}
