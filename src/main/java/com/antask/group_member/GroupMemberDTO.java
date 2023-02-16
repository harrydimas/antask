package com.antask.group_member;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupMemberDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    private UUID group;
}
