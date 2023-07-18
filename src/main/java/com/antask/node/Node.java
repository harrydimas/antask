package com.antask.node;

import com.antask.flow.Flow;
import com.antask.task.Task;
import com.antask.util.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Node extends BaseModel {

    public Node(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NodeTypeEnum nodeType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AssigneeTypeEnum assigneeType;

    @Column
    private String assignee;

    @Column(columnDefinition = "char(36)")
    private String approvedNode;

    @Column(columnDefinition = "char(36)")
    private String rejectedNode;

    @OneToMany(mappedBy = "node", fetch = FetchType.LAZY)
    private Set<Task> nodeTasks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flow_id", nullable = false)
    private Flow flow;

}
