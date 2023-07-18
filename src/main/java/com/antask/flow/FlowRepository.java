package com.antask.flow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FlowRepository extends JpaRepository<Flow, String> {

    @Query("FROM Flow WHERE name = :flow")
    Optional<Flow> findByName(String flow);
}
