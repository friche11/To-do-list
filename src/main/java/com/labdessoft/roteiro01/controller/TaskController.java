package com.labdessoft.roteiro01.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
public class TaskController extends Exception{

    @Autowired
     private TaskService taskService;

    @Operation(summary = "Lista todas as tarefas da lista")
    @GetMapping("/tasks")
    public List<Task> listAll(){
        return taskService.listAll();
    }

    @GetMapping("/task/{id}")
    public Optional<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
    
    @PostMapping("/create/task")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/edit/{id}")
    public Task editTask(@RequestBody Task task) {
        return taskService.editTask(task);
    }
    
    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
