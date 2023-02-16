package com.antask.task;

import com.antask.node.Node;
import com.antask.node.NodeRepository;
import com.antask.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final NodeRepository nodeRepository;

    public List<TaskDTO> findAll() {
        final List<Task> tasks = taskRepository.findAll(Sort.by("id"));
        return tasks.stream().map(task -> mapToDTO(task, new TaskDTO())).collect(Collectors.toList());
    }

    public TaskDTO get(final UUID id) {
        return taskRepository
            .findById(id)
            .map(task -> mapToDTO(task, new TaskDTO()))
            .orElseThrow(() -> new NotFoundException());
    }

    public UUID create(final TaskDTO taskDTO) {
        final Task task = new Task();
        mapToEntity(taskDTO, task);
        return taskRepository.save(task).getId();
    }

    public void update(final UUID id, final TaskDTO taskDTO) {
        final Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException());
        mapToEntity(taskDTO, task);
        taskRepository.save(task);
    }

    public void delete(final UUID id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO mapToDTO(final Task task, final TaskDTO taskDTO) {
        taskDTO.setId(task.getId());
        taskDTO.setAssignee(task.getAssignee());
        taskDTO.setVariables(task.getVariables());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setNode(task.getNode() == null ? null : task.getNode().getId());
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
