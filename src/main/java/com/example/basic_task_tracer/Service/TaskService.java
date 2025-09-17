package com.example.basic_task_tracer.Service;

import com.example.basic_task_tracer.Dto.TaskDto;
import com.example.basic_task_tracer.Entity.Task;
import com.example.basic_task_tracer.Entity.User;
import com.example.basic_task_tracer.Repositories.TaskRepository;
import com.example.basic_task_tracer.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor

public class TaskService {
private final TaskRepository taskRepository;
private final UserRepository userRepository;




    @CacheEvict(value = "tasks", allEntries = true)
    public Task createTask(Long userId , TaskDto taskDto) {
        User userTask = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("пользователь с таким id не найден"));
        Task task = Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .completed(taskDto.getCompleted())
                .owner(userTask).
                maskedNumber(taskDto.getMaskedNumber())

                .build();

        return taskRepository.saveAndFlush(task);
    }


    @Transactional
    public void deleteTask(Long userId, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задача с таким id не найдена"));

        if (!task.getOwner().getId().equals(userId)) {
            throw new RuntimeException("У пользователя нет доступа к этой задаче");
        }

        taskRepository.delete(task);
    }

    @Transactional
    public Task updateTask(Long userId, Long taskId, TaskDto taskUpdateDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задача с таким id не найдена"));

        if (!task.getOwner().getId().equals(userId)) {
            throw new RuntimeException("У пользователя нет доступа к этой задаче");
        }

        if (taskUpdateDto.getTitle() != null) {
            task.setTitle(taskUpdateDto.getTitle());
        }
        if (taskUpdateDto.getDescription() != null) {
            task.setDescription(taskUpdateDto.getDescription());
        }
        if (taskUpdateDto.getCompleted() != null) {
            task.setCompleted(taskUpdateDto.getCompleted());
        }

        return taskRepository.saveAndFlush(task);
    }

}

