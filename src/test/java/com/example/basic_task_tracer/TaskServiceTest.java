package com.example.basic_task_tracer;

import com.example.basic_task_tracer.Entity.Task;
import com.example.basic_task_tracer.Entity.User;
import com.example.basic_task_tracer.Repositories.TaskRepository;
import com.example.basic_task_tracer.Repositories.UserRepository;
import com.example.basic_task_tracer.Dto.TaskDto;
import com.example.basic_task_tracer.Service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateTask_success() {
        Long userId = 1L;
        TaskDto taskDto = TaskDto.builder()
                .title("Title")
                .description("Description")
                .completed(false)
                .maskedNumber("1234-****-5678")
                .build();

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.saveAndFlush(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        Task createdTask = taskService.createTask(userId, taskDto);

        assertNotNull(createdTask);
        assertEquals("Title", createdTask.getTitle());
        assertEquals(user, createdTask.getOwner());
        verify(taskRepository, times(1)).saveAndFlush(any(Task.class));
    }

    @Test
    void testCreateTask_userNotFound() {
        Long userId = 1L;
        TaskDto taskDto = TaskDto.builder()
                .title("Title")
                .description("Description")
                .completed(false)
                .maskedNumber("1234-****-5678")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                taskService.createTask(userId, taskDto)
        );

        assertEquals("пользователь с таким id не найден", exception.getMessage());
    }

    @Test
    void testUpdateTask_success() {
        Long userId = 1L;
        Long taskId = 100L;
        TaskDto updateDto = TaskDto.builder()
                .title("Updated")
                .description("New desc")
                .completed(true)
                .build();

        User user = new User();
        user.setId(userId);

        Task task = Task.builder()
                .id(taskId)
                .title("Old")
                .description("Old desc")
                .completed(false)
                .owner(user)
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.saveAndFlush(task)).thenReturn(task);

        Task updatedTask = taskService.updateTask(userId, taskId, updateDto);

        assertEquals("Updated", updatedTask.getTitle());
        assertEquals("New desc", updatedTask.getDescription());
        assertTrue(updatedTask.isCompleted());
    }

    @Test
    void testUpdateTask_notOwner() {
        Long userId = 1L;
        Long taskId = 100L;
        TaskDto updateDto = TaskDto.builder()
                .title("Updated")
                .build();

        User owner = new User();
        owner.setId(2L);

        Task task = Task.builder()
                .id(taskId)
                .title("Old")
                .description("Old desc")
                .completed(false)
                .owner(owner)
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                taskService.updateTask(userId, taskId, updateDto)
        );

        assertEquals("У пользователя нет доступа к этой задаче", exception.getMessage());
    }

    @Test
    void testDeleteTask_success() {
        Long userId = 1L;
        Long taskId = 100L;

        User user = new User();
        user.setId(userId);

        Task task = Task.builder()
                .id(taskId)
                .owner(user)
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.deleteTask(userId, taskId);

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void testDeleteTask_notOwner() {
        Long userId = 1L;
        Long taskId = 100L;

        User owner = new User();
        owner.setId(2L);

        Task task = Task.builder()
                .id(taskId)
                .owner(owner)
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                taskService.deleteTask(userId, taskId)
        );

        assertEquals("У пользователя нет доступа к этой задаче", exception.getMessage());
    }
}
