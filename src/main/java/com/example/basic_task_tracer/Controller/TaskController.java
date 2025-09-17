package com.example.basic_task_tracer.Controller;

import com.example.basic_task_tracer.Dto.AckDto;
import com.example.basic_task_tracer.Dto.TaskDto;
import com.example.basic_task_tracer.Dto.TaskMapper;
import com.example.basic_task_tracer.Entity.Task;
import com.example.basic_task_tracer.Repositories.TaskRepository;
import com.example.basic_task_tracer.Service.TaskService;
import com.example.basic_task_tracer.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @PutMapping("/api/users/{userId}/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long userId,
            @PathVariable Long taskId,
            @RequestBody TaskDto taskUpdateDto
    ) {
        Task updatedTask = taskService.updateTask(userId, taskId, taskUpdateDto);
        return ResponseEntity.ok(updatedTask);
    }


    @CacheEvict(value = "tasksCache", allEntries = true)
    @PostMapping("/api/users/{userId}/tasks")
    public ResponseEntity<Task> createTask(
            @PathVariable Long userId,
            @RequestBody TaskDto taskDto
    ) {
        Task createdTask = taskService.createTask(userId, taskDto);
        return ResponseEntity.ok(createdTask);
    }



    @CacheEvict(value = "tasksCache", allEntries = true)
    @DeleteMapping("/api/users/{userId}/tasks/{taskId}")
    public AckDto deleteCard(@PathVariable("taskId") Long cardId){

        Task card = getTaskOrThrowException(cardId);
        taskRepository.delete(card);
        return AckDto.builder().answer(true).build();


    }
    @Cacheable(value = "tasksCache")
    @GetMapping("/api/tasks")
    public List<TaskDto> getAllTasks() {
        return taskRepository.streamAllBy()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "tasksCache")
    @GetMapping("/api/tasks/{taskId}")
    public TaskDto getTaskById(@PathVariable Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task with id " + taskId + " not found"));
        return taskMapper.toDto(task);
    }

    private Task getTaskOrThrowException(Long cardId) {

        return taskRepository
                .findById(cardId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Card with \"%s\" id doesn't exist.",
                                        cardId
                                )
                        )
                );
    }

}
