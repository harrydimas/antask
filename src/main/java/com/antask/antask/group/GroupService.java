package com.antask.antask.group;

import com.antask.antask.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

  private final GroupRepository groupRepository;

  public List<GroupDTO> findAll() {
    final List<Group> groups = groupRepository.findAll(Sort.by("id"));
    return groups.stream().map(group -> mapToDTO(group, new GroupDTO())).collect(Collectors.toList());
  }

  public GroupDTO get(final UUID id) {
    return groupRepository
      .findById(id)
      .map(group -> mapToDTO(group, new GroupDTO()))
      .orElseThrow(() -> new NotFoundException());
  }

  public UUID create(final GroupDTO groupDTO) {
    final Group group = new Group();
    mapToEntity(groupDTO, group);
    return groupRepository.save(group).getId();
  }

  public void update(final UUID id, final GroupDTO groupDTO) {
    final Group group = groupRepository.findById(id).orElseThrow(() -> new NotFoundException());
    mapToEntity(groupDTO, group);
    groupRepository.save(group);
  }

  public void delete(final UUID id) {
    groupRepository.deleteById(id);
  }

  private GroupDTO mapToDTO(final Group group, final GroupDTO groupDTO) {
    groupDTO.setId(group.getId());
    groupDTO.setName(group.getName());
    return groupDTO;
  }

  private Group mapToEntity(final GroupDTO groupDTO, final Group group) {
    group.setName(groupDTO.getName());
    return group;
  }
}
