package com.antask.flow;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlowRepository extends JpaRepository<Flow, UUID> {

    @Query("FROM Flow WHERE name = :flow")
    Optional<Flow> findByName(String flow);
}
