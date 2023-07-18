package com.antask.client;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {

    private String id;

    @NotNull
    @Size(max = 255)
    private String name;
}
