package com.antask.node;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private NodeTypeEnum nodeType;

    @NotNull
    private AssigneeTypeEnum assigneeType;

    @Size(max = 255)
    private String assignee;

    private String approvedNode;

    private String rejectedNode;

    @NotNull
    private UUID flow;
}
