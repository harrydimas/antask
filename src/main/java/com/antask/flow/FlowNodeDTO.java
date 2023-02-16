package com.antask.flow;

import com.antask.node.NodeDTO;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlowNodeDTO {

    @NotNull
    private FlowDTO flow;

    @NotNull
    private List<NodeDTO> nodes;
}
