package com.antask.flow;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlowDTO {

    private String id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private String client;
}
