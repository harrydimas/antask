package com.antask.antask.flow;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowRepository extends JpaRepository<Flow, UUID> {}
