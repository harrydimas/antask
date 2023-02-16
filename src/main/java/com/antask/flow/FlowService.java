package com.antask.flow;

import com.antask.client.Client;
import com.antask.client.ClientRepository;
import com.antask.node.Node;
import com.antask.node.NodeDTO;
import com.antask.node.NodeService;
import com.antask.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FlowService {

    private final FlowRepository flowRepository;
    private final ClientRepository clientRepository;
    private final NodeService nodeService;

    public List<FlowDTO> findAll() {
        final List<Flow> flows = flowRepository.findAll(Sort.by("id"));
        return flows.stream().map(flow -> mapToDTO(flow, new FlowDTO())).collect(Collectors.toList());
    }

    public FlowDTO get(final UUID id) {
        return flowRepository
            .findById(id)
            .map(flow -> mapToDTO(flow, new FlowDTO()))
            .orElseThrow(() -> new NotFoundException());
    }

    public UUID create(final FlowDTO flowDTO) {
        final Flow flow = new Flow();
        mapToEntity(flowDTO, flow);
        return flowRepository.save(flow).getId();
    }

    @Transactional
    public UUID createBulk(final FlowNodeDTO dto) {
        final Flow flow = new Flow();
        mapToEntity(dto.getFlow(), flow);
        UUID flowId = flowRepository.save(flow).getId();
        for (int i = dto.getNodes().size() - 1; i > -1; i--) {
            var node = dto.getNodes().get(i);
            node.setFlow(flowId);
            nodeService.create(node);
        }
        return flowId;
    }

    public void update(final UUID id, final FlowDTO flowDTO) {
        final Flow flow = flowRepository.findById(id).orElseThrow(() -> new NotFoundException());
        mapToEntity(flowDTO, flow);
        flowRepository.save(flow);
    }

    public void delete(final UUID id) {
        flowRepository.deleteById(id);
    }

    private FlowDTO mapToDTO(final Flow flow, final FlowDTO flowDTO) {
        flowDTO.setId(flow.getId());
        flowDTO.setName(flow.getName());
        flowDTO.setClient(flow.getClient() == null ? null : flow.getClient().getId());
        return flowDTO;
    }

    private Flow mapToEntity(final FlowDTO flowDTO, final Flow flow) {
        flow.setName(flowDTO.getName());
        final Client client = flowDTO.getClient() == null
            ? null
            : clientRepository
                .findById(flowDTO.getClient())
                .orElseThrow(() -> new NotFoundException("client not found"));
        flow.setClient(client);
        return flow;
    }
}
