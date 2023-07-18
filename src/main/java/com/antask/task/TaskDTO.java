package com.antask.task;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {

    private String id;

    @NotNull
    @Size(max = 255)
    private String assignee;

    @NotNull
    private String variables;

    @NotNull
    private StatusTypeEnum status;

    @NotNull
    private String node;

    private String flow;
}
