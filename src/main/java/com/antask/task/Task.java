package com.antask.task;

import com.antask.node.Node;
import com.antask.util.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task extends BaseModel {

    @Column(nullable = false)
    private String assignee;

    @Column(nullable = false, columnDefinition = "longtext")
    private String variables;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusTypeEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private Node node;

}
