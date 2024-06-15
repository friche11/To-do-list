package com.labdessoft.roteiro01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Lista todas as tarefas da lista")
    @GetMapping("/tasks")
    public ResponseEntity<?> listAllTasks() {
        try {
            List<Task> tasks = taskService.getAllTasks();
            if (tasks.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar as tarefas.");
        }
    }

    @Operation(summary = "Lista uma tarefa específica da lista")
    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable int id) {
        try {
            Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada com o ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar a tarefa com o ID: " + id);
        }
    }

    @Operation(summary = "Cria uma nova tarefa para a lista")
    @PostMapping("/create/task")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a tarefa.");
        }
    }

    @Operation(summary = "Marca uma tarefa como concluída")
    @PutMapping("/complete/{id}")
    public ResponseEntity<?> completeTask(@PathVariable int id) {
        try {
            Task completedTask = taskService.completeTask(id);
            return ResponseEntity.ok(completedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada com o ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao marcar a tarefa como concluída.");
        }
    }

    @Operation(summary = "Edita uma tarefa existente da lista")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada com o ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao editar a tarefa com o ID: " + id);
        }
    }

    @Operation(summary = "Deleta uma tarefa existente da lista")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable int id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada com o ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar a tarefa com o ID: " + id);
        }
    }

}
