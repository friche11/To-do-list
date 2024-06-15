package com.labdessoft.roteiro01.unit.repository;

import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.enums.TaskType;
import com.labdessoft.roteiro01.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Should save task in the database")
    void shouldSaveTask() {
        // Given
        Task task = new Task();
        task.setDescription("Test Task");
        task.setType(TaskType.DATA);
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setCompleted(false);

        // When
        Task savedTask = taskRepository.save(task);

        // Then
        assertNotNull(savedTask);
        assertNotNull(savedTask.getId());
        assertEquals(task.getDescription(), savedTask.getDescription());
        assertEquals(task.getType(), savedTask.getType());
        assertEquals(task.getDueDate(), savedTask.getDueDate());
        assertEquals(task.isCompleted(), savedTask.isCompleted());
    }

    @Test
    @DisplayName("Should retrieve task by ID")
    void shouldRetrieveTaskById() {
        // Given
        Task task = new Task();
        task.setDescription("Test Task");
        task.setType(TaskType.DATA);
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setCompleted(false);
        Task savedTask = taskRepository.save(task);

        // When
        Optional<Task> retrievedTaskOptional = taskRepository.findById(savedTask.getId());

        // Then
        assertTrue(retrievedTaskOptional.isPresent());
        Task retrievedTask = retrievedTaskOptional.get();
        assertEquals(savedTask.getId(), retrievedTask.getId());
        assertEquals(savedTask.getDescription(), retrievedTask.getDescription());
        assertEquals(savedTask.getType(), retrievedTask.getType());
        assertEquals(savedTask.getDueDate(), retrievedTask.getDueDate());
        assertEquals(savedTask.isCompleted(), retrievedTask.isCompleted());
    }

    @Test
    @DisplayName("Should retrieve all tasks")
    void shouldRetrieveAllTasks() {
        // Given
        Task task1 = new Task();
        task1.setDescription("Task 1");
        task1.setType(TaskType.DATA);
        task1.setDueDate(LocalDate.now().plusDays(5));
        task1.setCompleted(false);
        Task task2 = new Task();
        task2.setDescription("Task 2");
        task2.setType(TaskType.DATA);
        task2.setDueDate(LocalDate.now().plusDays(7));
        task2.setCompleted(true);
        taskRepository.save(task1);
        taskRepository.save(task2);

        // When
        List<Task> allTasks = taskRepository.findAll();

        // Then
        assertEquals(2, allTasks.size());
    }

    @Test
    @DisplayName("Should delete task by ID")
    void shouldDeleteTaskById() {
        // Given
        Task task = new Task();
        task.setDescription("Test Task");
        task.setType(TaskType.DATA);
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setCompleted(false);
        Task savedTask = taskRepository.save(task);

        // When
        taskRepository.deleteById(savedTask.getId());

        // Then
        assertFalse(taskRepository.existsById(savedTask.getId()));
    }

}
