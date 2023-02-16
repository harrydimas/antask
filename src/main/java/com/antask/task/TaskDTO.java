package com.antask.task;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String assignee;

    @NotNull
    private String variables;

    @NotNull
    private StatusTypeEnum status;

    @NotNull
    private UUID node;
}
