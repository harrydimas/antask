package com.antask.group;

import com.antask.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public List<GroupDTO> findAll() {
        final List<Group> groups = groupRepository.findAll(Sort.by("id"));
        return groups.stream().map(group -> mapToDTO(group, new GroupDTO())).collect(Collectors.toList());
    }

    public GroupDTO get(final String id) {
        return groupRepository
            .findById(id)
            .map(group -> mapToDTO(group, new GroupDTO()))
            .orElseThrow(NotFoundException::new);
    }

    public String create(final GroupDTO groupDTO) {
        final Group group = new Group();
        mapToEntity(groupDTO, group);
        return groupRepository.save(group).getId();
    }

    public void update(final String id, final GroupDTO groupDTO) {
        final Group group = groupRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(groupDTO, group);
        groupRepository.save(group);
    }

    public void delete(final String id) {
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
