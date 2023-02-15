package com.antask.antask.group_member;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/groupMembers", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupMemberResource {

  private final GroupMemberService groupMemberService;

  public GroupMemberResource(final GroupMemberService groupMemberService) {
    this.groupMemberService = groupMemberService;
  }

  @GetMapping
  public ResponseEntity<List<GroupMemberDTO>> getAllGroupMembers() {
    return ResponseEntity.ok(groupMemberService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<GroupMemberDTO> getGroupMember(@PathVariable final Long id) {
    return ResponseEntity.ok(groupMemberService.get(id));
  }

  @PostMapping
  @ApiResponse(responseCode = "201")
  public ResponseEntity<Long> createGroupMember(@RequestBody @Valid final GroupMemberDTO groupMemberDTO) {
    return new ResponseEntity<>(groupMemberService.create(groupMemberDTO), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateGroupMember(
    @PathVariable final Long id,
    @RequestBody @Valid final GroupMemberDTO groupMemberDTO
  ) {
    groupMemberService.update(id, groupMemberDTO);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  @ApiResponse(responseCode = "204")
  public ResponseEntity<Void> deleteGroupMember(@PathVariable final Long id) {
    groupMemberService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
