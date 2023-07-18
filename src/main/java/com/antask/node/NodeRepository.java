package com.antask.node;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NodeRepository extends JpaRepository<Node, String> {
    @Query("FROM Node WHERE name = :name AND (flow.id = :flow OR flow.name = :flow) ORDER BY dateCreated DESC")
    Node findByNameAndFlow(String name, String flow);

    @Query("SELECT approvedNode FROM Node WHERE name = 'START' AND (flow.id = :flow OR flow.name = :flow) ORDER BY dateCreated DESC")
    Optional<String> findStartByFlow(String flow);
}
