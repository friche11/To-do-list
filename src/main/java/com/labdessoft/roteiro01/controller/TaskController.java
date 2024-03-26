package com.labdessoft.roteiro01.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.labdessoft.roteiro01.repository.TaskRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class TaskController extends Exception{

    @Autowired
     private TaskRepository taskRepository;

@Operation(summary = "Lista todas as tarefas da lista")
@GetMapping("/tasks")
   public ResponseEntity<List<Task>> listAll() {
try{
    List<Task> taskList = new ArrayList<Task>();
    taskRepository.findAll().forEach(taskList::add);
    if(taskList.isEmpty()){
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
    return new ResponseEntity<>(taskList, HttpStatus.OK);
} catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
}
}

@Operation(summary = "Lista uma tarefa específica da lista")
@GetMapping("/task/{id}")
public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
    try {
        Optional<Task> taskData = taskRepository.findById(id);
        if (taskData.isPresent()) {
            return new ResponseEntity<>(taskData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@Operation(summary = "Cria uma nova tarefa para a lista")
@PostMapping("/create/task")
public ResponseEntity<Task> createTask(@RequestBody Task task) {
    try {
        Task _task = taskRepository.save(task);
        return new ResponseEntity<>(_task, HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@Operation(summary = "Edita uma tarefa existente da lista")
@PutMapping("/edit/{id}")
public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
    try {
        if (taskRepository.existsById(id)) {
            Task updatedTask = taskRepository.save(task);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@Operation(summary = "Deleta uma tarefa existente da lista")
@DeleteMapping("/delete/{id}")
public ResponseEntity<HttpStatus> deleteTask(@PathVariable Long id) {
    try {
        taskRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}
