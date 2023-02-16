package com.antask.flow;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlowDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private UUID cliet;
}
