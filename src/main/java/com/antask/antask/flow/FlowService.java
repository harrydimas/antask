package com.antask.antask.flow;

import com.antask.antask.client.Client;
import com.antask.antask.client.ClientRepository;
import com.antask.antask.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlowService {

  private final FlowRepository flowRepository;
  private final ClientRepository clientRepository;

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
    flowDTO.setCliet(flow.getCliet() == null ? null : flow.getCliet().getId());
    return flowDTO;
  }

  private Flow mapToEntity(final FlowDTO flowDTO, final Flow flow) {
    flow.setName(flowDTO.getName());
    final Client cliet = flowDTO.getCliet() == null
      ? null
      : clientRepository.findById(flowDTO.getCliet()).orElseThrow(() -> new NotFoundException("cliet not found"));
    flow.setCliet(cliet);
    return flow;
  }
}
