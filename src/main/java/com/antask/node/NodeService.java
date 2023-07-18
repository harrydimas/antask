package com.antask.node;

import com.antask.flow.Flow;
import com.antask.flow.FlowRepository;
import com.antask.group.GroupRepositoryImpl;
import com.antask.util.BadRequestException;
import com.antask.util.NotFoundException;
import com.antask.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NodeService {

    private final NodeRepository nodeRepository;
    private final FlowRepository flowRepository;
    private final GroupRepositoryImpl groupRepositoryImpl;

    public List<NodeDTO> findAll() {
        final List<Node> nodes = nodeRepository.findAll(Sort.by("id"));
        return nodes.stream().map(node -> mapToDTO(node, new NodeDTO())).collect(Collectors.toList());
    }

    public NodeDTO get(final String id) {
        return nodeRepository
            .findById(id)
            .map(node -> mapToDTO(node, new NodeDTO()))
            .orElseThrow(() -> new NotFoundException());
    }

    public String create(final NodeDTO nodeDTO) {
        final Node node = new Node();
        mapToEntity(nodeDTO, node);
        return nodeRepository.save(node).getId();
    }

    public void update(final String id, final NodeDTO nodeDTO) {
        final Node node = nodeRepository.findById(id).orElseThrow(() -> new NotFoundException());
        mapToEntity(nodeDTO, node);
        nodeRepository.save(node);
    }

    public void delete(final String id) {
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
        if (!StringUtils.isEmpty(nodeDTO.getName())) {
            var existingNode = nodeRepository.findByNameAndFlow(nodeDTO.getName(), nodeDTO.getFlow());
            if (Objects.nonNull(existingNode)) throw new BadRequestException(nodeDTO.getName() + " is exists");
            node.setName(nodeDTO.getName());
        } else {
            throw new BadRequestException("name is empty");
        }

        node.setNodeType(nodeDTO.getNodeType());
        if (
            (node.getNodeType() == NodeTypeEnum.START && nodeDTO.getAssigneeType() != AssigneeTypeEnum.START) ||
            (node.getNodeType() == NodeTypeEnum.CONDITION && nodeDTO.getAssigneeType() != AssigneeTypeEnum.CONDITION) ||
            (node.getNodeType() == NodeTypeEnum.END && nodeDTO.getAssigneeType() != AssigneeTypeEnum.END)
        ) throw new BadRequestException("assignee type must be " + node.getNodeType());

        if (
            node.getNodeType() == NodeTypeEnum.TASK &&
            (
                Objects.isNull(nodeDTO.getAssigneeType()) ||
                (
                    nodeDTO.getAssigneeType() != AssigneeTypeEnum.EMAIL &&
                    nodeDTO.getAssigneeType() != AssigneeTypeEnum.GROUP
                )
            )
        ) {
            throw new BadRequestException("assignee type for TASK must be EMAIL or GROUP");
        }

        node.setAssigneeType(nodeDTO.getAssigneeType());

        if (
            node.getAssigneeType() == AssigneeTypeEnum.START ||
            node.getAssigneeType() == AssigneeTypeEnum.CONDITION ||
            node.getAssigneeType() == AssigneeTypeEnum.END
        ) node.setAssignee(null); else if (node.getAssigneeType() == AssigneeTypeEnum.GROUP) {
            var existingGroup = groupRepositoryImpl.findByNameAndFlow(nodeDTO.getAssignee(), nodeDTO.getFlow());
            if (Objects.isNull(existingGroup))
                throw new NotFoundException("group " + nodeDTO.getAssignee() + " not found");
            node.setAssignee(existingGroup.getId().toString());
        } else {
            node.setAssignee(nodeDTO.getAssignee());
        }

        if (!StringUtils.isEmpty(nodeDTO.getApprovedNode())) {
            var existingNode = nodeRepository.findByNameAndFlow(nodeDTO.getApprovedNode(), nodeDTO.getFlow());
            if (Objects.isNull(existingNode))
                throw new NotFoundException("approved node " + nodeDTO.getApprovedNode() + " not found");
            node.setApprovedNode(existingNode.getId().toString());
        } else {
            node.setApprovedNode(null);
        }

        if (!StringUtils.isEmpty(nodeDTO.getRejectedNode())) {
            var existingNode = nodeRepository.findByNameAndFlow(nodeDTO.getRejectedNode(), nodeDTO.getFlow());
            if (Objects.isNull(existingNode))
                throw new NotFoundException("rejected node " + nodeDTO.getRejectedNode() + " not found");
            node.setRejectedNode(existingNode.getId().toString());
        } else {
            node.setRejectedNode(null);
        }

        final Flow flow = nodeDTO.getFlow() == null
            ? null
            : flowRepository.findById(nodeDTO.getFlow()).orElseThrow(() -> new NotFoundException("flow not found"));
        node.setFlow(flow);
        return node;
    }
}
