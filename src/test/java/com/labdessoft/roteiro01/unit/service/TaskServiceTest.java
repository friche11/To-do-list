package com.labdessoft.roteiro01.unit.service;

import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.enums.TaskType;
import com.labdessoft.roteiro01.mock.TaskMock;
import com.labdessoft.roteiro01.repository.TaskRepository;
import com.labdessoft.roteiro01.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setup() {
        List<Task> tasks = TaskMock.createTasks();
        Page<Task> taskPage = new PageImpl<>(tasks);

        // Configuração para findAll com qualquer Pageable
    Mockito.lenient().when(taskRepository.findAll(Mockito.any(Pageable.class))).thenReturn(taskPage);

    // Configuração para findById retornando uma Task com ID 1
    Mockito.lenient().when(taskRepository.findById(1)).thenReturn(Optional.of(TaskMock.createTask(1)));

    // Configuração para save retornando a Task passada como argumento
    Mockito.lenient().when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArguments()[0]);
        
    }

    @Test
    @DisplayName("Should return all tasks with pagination")
    public void should_return_all_tasks_with_pagination() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(
                Sort.Order.asc("name"),
                Sort.Order.desc("id")));
        Page<Task> tasks = taskService.listAll(pageable);

        assertEquals(1, tasks.getTotalPages());
        assertEquals(2, tasks.getNumberOfElements());
        assertNotNull(tasks);
    }

    @Test
    @DisplayName("Should return a task by ID")
    public void should_return_task_by_id() {
        Task task = taskService.getTaskById(1);
        assertNotNull(task);
        assertEquals(1, task.getId());
    }

    @Test
    @DisplayName("Should create a new task")
    public void should_create_new_task() {
        Task task = new Task();
        task.setDescription("New task description");
        task.setType(TaskType.DATA);
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setCompleted(false);

        Task createdTask = taskService.createTask(task);
        assertNotNull(createdTask);
        assertEquals("New task description", createdTask.getDescription());
    }

    @Test
    @DisplayName("Should complete a task")
    public void should_complete_task() {
        Task completedTask = taskService.completeTask(1);
        assertTrue(completedTask.isCompleted());
        assertEquals("Concluída", completedTask.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when creating task with invalid description")
    public void should_throw_exception_for_invalid_description() {
        Task task = new Task();
        task.setDescription("Short");
        task.setType(TaskType.DATA);
        task.setDueDate(LocalDate.now().plusDays(5));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(task);
        });

        assertEquals("A descrição da tarefa deve possuir pelo menos 10 caracteres.", exception.getMessage());
    }
}
