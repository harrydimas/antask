package com.antask.antask.group_member;

import com.antask.antask.group.Group;
import com.antask.antask.group.GroupRepository;
import com.antask.antask.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;

    public List<GroupMemberDTO> findAll() {
        final List<GroupMember> groupMembers = groupMemberRepository.findAll(Sort.by("id"));
        return groupMembers
            .stream()
            .map(groupMember -> mapToDTO(groupMember, new GroupMemberDTO()))
            .collect(Collectors.toList());
    }

    public GroupMemberDTO get(final Long id) {
        return groupMemberRepository
            .findById(id)
            .map(groupMember -> mapToDTO(groupMember, new GroupMemberDTO()))
            .orElseThrow(() -> new NotFoundException());
    }

    public Long create(final GroupMemberDTO groupMemberDTO) {
        final GroupMember groupMember = new GroupMember();
        mapToEntity(groupMemberDTO, groupMember);
        return groupMemberRepository.save(groupMember).getId();
    }

    public void update(final Long id, final GroupMemberDTO groupMemberDTO) {
        final GroupMember groupMember = groupMemberRepository.findById(id).orElseThrow(() -> new NotFoundException());
        mapToEntity(groupMemberDTO, groupMember);
        groupMemberRepository.save(groupMember);
    }

    public void delete(final Long id) {
        groupMemberRepository.deleteById(id);
    }

    private GroupMemberDTO mapToDTO(final GroupMember groupMember, final GroupMemberDTO groupMemberDTO) {
        groupMemberDTO.setId(groupMember.getId());
        groupMemberDTO.setEmail(groupMember.getEmail());
        groupMemberDTO.setGroup(groupMember.getGroup() == null ? null : groupMember.getGroup().getId());
        return groupMemberDTO;
    }

    private GroupMember mapToEntity(final GroupMemberDTO groupMemberDTO, final GroupMember groupMember) {
        groupMember.setEmail(groupMemberDTO.getEmail());
        final Group group = groupMemberDTO.getGroup() == null
            ? null
            : groupRepository
                .findById(groupMemberDTO.getGroup())
                .orElseThrow(() -> new NotFoundException("group not found"));
        groupMember.setGroup(group);
        return groupMember;
    }
}
