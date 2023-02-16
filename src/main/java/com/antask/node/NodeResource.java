package com.antask.node;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
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
@RequestMapping(value = "/api/nodes", produces = MediaType.APPLICATION_JSON_VALUE)
public class NodeResource {

    private final NodeService nodeService;

    public NodeResource(final NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping
    public ResponseEntity<List<NodeDTO>> getAllNodes() {
        return ResponseEntity.ok(nodeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NodeDTO> getNode(@PathVariable final UUID id) {
        return ResponseEntity.ok(nodeService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createNode(@RequestBody @Valid final NodeDTO nodeDTO) {
        return new ResponseEntity<>(nodeService.create(nodeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNode(@PathVariable final UUID id, @RequestBody @Valid final NodeDTO nodeDTO) {
        nodeService.update(id, nodeDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteNode(@PathVariable final UUID id) {
        nodeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
