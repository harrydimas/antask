package com.antask.flow;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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

import java.util.List;

@RestController
@RequestMapping(value = "/api/flows", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlowResource {

    private final FlowService flowService;

    public FlowResource(final FlowService flowService) {
        this.flowService = flowService;
    }

    @GetMapping
    public ResponseEntity<List<FlowDTO>> getAllFlows() {
        return ResponseEntity.ok(flowService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlowDTO> getFlow(@PathVariable final String id) {
        return ResponseEntity.ok(flowService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createFlow(@RequestBody @Valid final FlowDTO flowDTO) {
        return new ResponseEntity<>(flowService.create(flowDTO), HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createFlowBulk(@RequestBody @Valid final FlowNodeDTO flowNodeDTO) {
        return new ResponseEntity<>(flowService.createBulk(flowNodeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFlow(@PathVariable final String id, @RequestBody @Valid final FlowDTO flowDTO) {
        flowService.update(id, flowDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFlow(@PathVariable final String id) {
        flowService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
