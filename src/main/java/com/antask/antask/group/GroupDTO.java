package com.antask.antask.group;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDTO {

  private UUID id;

  @NotNull
  @Size(max = 255)
  private String name;
}
