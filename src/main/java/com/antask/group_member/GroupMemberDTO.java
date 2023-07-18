package com.antask.group_member;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupMemberDTO {

    private String id;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    private String group;
}
