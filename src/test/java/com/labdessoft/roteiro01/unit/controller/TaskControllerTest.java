package com.labdessoft.roteiro01.unit.controller;

import com.labdessoft.roteiro01.controller.TaskController;
import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.mock.TaskMock;
import com.labdessoft.roteiro01.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private List<Task> tasks;

    @BeforeEach
    void setUp() {
        tasks = TaskMock.createTasks();
    }

    @Test
    @DisplayName("Should list all tasks")
    void shouldListAllTasks() {
        when(taskService.getAllTasks()).thenReturn(tasks);

        ResponseEntity<?> response = taskController.listAllTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(tasks, response.getBody());
    }

    @Test
    @DisplayName("Should return no content when no tasks are found")
    void shouldReturnNoContentWhenNoTasksAreFound() {
        when(taskService.getAllTasks()).thenReturn(Arrays.asList());

        ResponseEntity<?> response = taskController.listAllTasks();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return a task by ID")
    void shouldReturnTaskById() {
        Task task = TaskMock.createTask(1);
        when(taskService.getTaskById(1)).thenReturn(task);

        ResponseEntity<?> response = taskController.getTaskById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(task, response.getBody());
    }

    @Test
    @DisplayName("Should return not found when task is not found")
    void shouldReturnNotFoundWhenTaskIsNotFound() {
        when(taskService.getTaskById(1)).thenThrow(new RuntimeException());

        ResponseEntity<?> response = taskController.getTaskById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Should create a new task")
    void shouldCreateNewTask() {
        Task task = TaskMock.createTask(1);
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        ResponseEntity<?> response = taskController.createTask(task);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(task, response.getBody());
    }

    @Test
    @DisplayName("Should complete a task")
    void shouldCompleteTask() {
        Task task = TaskMock.createTask(1);
        task.setCompleted(true);
        when(taskService.completeTask(1)).thenReturn(task);

        ResponseEntity<?> response = taskController.completeTask(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(task, response.getBody());
    }

    @Test
    @DisplayName("Should update a task")
    void shouldUpdateTask() {
        Task task = TaskMock.createTask(1);
        task.setDescription("Updated Task");
        when(taskService.updateTask(anyInt(), any(Task.class))).thenReturn(task);

        ResponseEntity<?> response = taskController.updateTask(1, task);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(task, response.getBody());
    }

    @Test
    @DisplayName("Should delete a task")
    void shouldDeleteTask() {
        doNothing().when(taskService).deleteTask(1);

        ResponseEntity<?> response = taskController.deleteTask(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return not found when deleting a non-existing task")
    void shouldReturnNotFoundWhenDeletingNonExistingTask() {
        doThrow(new RuntimeException()).when(taskService).deleteTask(1);

        ResponseEntity<?> response = taskController.deleteTask(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
