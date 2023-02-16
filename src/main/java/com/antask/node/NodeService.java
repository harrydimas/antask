package com.antask.node;

import com.antask.flow.Flow;
import com.antask.flow.FlowRepository;
import com.antask.util.BadRequestException;
import com.antask.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NodeService {

    private final NodeRepository nodeRepository;
    private final FlowRepository flowRepository;

    public List<NodeDTO> findAll() {
        final List<Node> nodes = nodeRepository.findAll(Sort.by("id"));
        return nodes.stream().map(node -> mapToDTO(node, new NodeDTO())).collect(Collectors.toList());
    }

    public NodeDTO get(final UUID id) {
        return nodeRepository
            .findById(id)
            .map(node -> mapToDTO(node, new NodeDTO()))
            .orElseThrow(() -> new NotFoundException());
    }

    public UUID create(final NodeDTO nodeDTO) {
        final Node node = new Node();
        mapToEntity(nodeDTO, node);
        return nodeRepository.save(node).getId();
    }

    public void update(final UUID id, final NodeDTO nodeDTO) {
        final Node node = nodeRepository.findById(id).orElseThrow(() -> new NotFoundException());
        mapToEntity(nodeDTO, node);
        nodeRepository.save(node);
    }

    public void delete(final UUID id) {
        nodeRepository.deleteById(id);
    }

    private NodeDTO mapToDTO(final Node node, final NodeDTO nodeDTO) {
        nodeDTO.setId(node.getId());
        nodeDTO.setName(node.getName());
        nodeDTO.setNodeType(node.getNodeType());
        nodeDTO.setAssigneeType(node.getAssigneeType());
        nodeDTO.setAssignee(node.getAssignee());
        nodeDTO.setApprovedNode(node.getApprovedNode());
        nodeDTO.setRejectedNode(node.getRejectedNode());
        nodeDTO.setFlow(node.getFlow() == null ? null : node.getFlow().getId());
        return nodeDTO;
    }

    private Node mapToEntity(final NodeDTO nodeDTO, final Node node) {
        node.setName(nodeDTO.getName());
        node.setNodeType(nodeDTO.getNodeType());
        if (
            (node.getNodeType() == NodeTypeEnum.START && nodeDTO.getAssigneeType() != AssigneeTypeEnum.START) ||
            (node.getNodeType() == NodeTypeEnum.CONDITION && nodeDTO.getAssigneeType() != AssigneeTypeEnum.CONDITION) ||
            (node.getNodeType() == NodeTypeEnum.END && nodeDTO.getAssigneeType() != AssigneeTypeEnum.END)
        ) throw new BadRequestException("assignee type must be " + node.getNodeType());
        node.setAssigneeType(nodeDTO.getAssigneeType());
        if (
            node.getNodeType() == NodeTypeEnum.START ||
            node.getNodeType() == NodeTypeEnum.CONDITION ||
            node.getNodeType() == NodeTypeEnum.END
        ) node.setAssignee(null); else node.setAssignee(nodeDTO.getAssignee());
        node.setApprovedNode(nodeDTO.getApprovedNode());
        node.setRejectedNode(nodeDTO.getRejectedNode());
        final Flow flow = nodeDTO.getFlow() == null
            ? null
            : flowRepository.findById(nodeDTO.getFlow()).orElseThrow(() -> new NotFoundException("flow not found"));
        node.setFlow(flow);
        return node;
    }
}
