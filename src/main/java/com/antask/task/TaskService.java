package com.antask.task;

import com.antask.flow.Flow;
import com.antask.flow.FlowRepository;
import com.antask.node.AssigneeTypeEnum;
import com.antask.node.Node;
import com.antask.node.NodeRepository;
import com.antask.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final NodeRepository nodeRepository;
    private final FlowRepository flowRepository;

    public List<TaskDTO> findAll() {
        final List<Task> tasks = taskRepository.findAll(Sort.by("id"));
        return tasks.stream().map(task -> mapToDTO(task, new TaskDTO())).collect(Collectors.toList());
    }

    public TaskDTO get(final String id) {
        return taskRepository
            .findById(id)
            .map(task -> mapToDTO(task, new TaskDTO()))
            .orElseThrow(NotFoundException::new);
    }

    public String create(final TaskDTO taskDTO) {
        final Task task = new Task();
        mapToEntity(taskDTO, task);
        return taskRepository.save(task).getId();
    }

    public String submitNew(final TaskResource.TaskSubmission taskSubmission) {
        final Task task = new Task();
        task.setVariables(taskSubmission.jsonVariables());
        task.setStatus(StatusTypeEnum.NEW);
        var nodeId = nodeRepository.findStartByFlow(taskSubmission.flowName()).orElseThrow(() -> new NotFoundException("flow not found"));
        var node = nodeRepository.findById(nodeId).orElseThrow(() -> new NotFoundException("node not found"));
        task.setNode(node);
        if(node.getAssigneeType() == AssigneeTypeEnum.EMAIL)
            task.setAssignee(node.getAssignee());
        else throw new IllegalArgumentException();
        return taskRepository.save(task).getId();
    }

    public void update(final String id, final TaskDTO taskDTO) {
        final Task task = taskRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(taskDTO, task);
        taskRepository.save(task);
    }

    public void delete(final String id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO mapToDTO(final Task task, final TaskDTO taskDTO) {
        taskDTO.setId(task.getId());
        taskDTO.setAssignee(task.getAssignee());
        taskDTO.setVariables(task.getVariables());
        taskDTO.setStatus(task.getStatus());
        if(Objects.nonNull(task.getNode())) {
            var node = nodeRepository.findById(task.getNode().getId()).orElse(null);
            if(Objects.nonNull(node)) {
                taskDTO.setNode(node.getName());
                var flow = flowRepository.findById(node.getFlow().getId()).orElse(null);
                if(Objects.nonNull(flow)) {
                    taskDTO.setFlow(flow.getName());
                }
            }
        }
        return taskDTO;
    }

    private Task mapToEntity(final TaskDTO taskDTO, final Task task) {
        task.setAssignee(taskDTO.getAssignee());
        task.setVariables(taskDTO.getVariables());
        task.setStatus(taskDTO.getStatus());
        final Node node = taskDTO.getNode() == null
            ? null
            : nodeRepository.findById(taskDTO.getNode()).orElseThrow(() -> new NotFoundException("node not found"));
        task.setNode(node);
        return task;
    }
}
