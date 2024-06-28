package com.labdessoft.roteiro01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.repository.TaskRepository;
import java.time.LocalDate;
import java.util.List;
import java.time.temporal.ChronoUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.labdessoft.roteiro01.enums.TaskType;
import com.labdessoft.roteiro01.enums.TaskPriority;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Page<Task> listAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        tasks.forEach(task -> {
            updateTaskStatus(task, LocalDate.now());
            taskRepository.save(task); 
        });
        return tasks;
    }
    

    public Task getTaskById(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        updateTaskStatus(task, LocalDate.now());
        taskRepository.save(task); 
        return task;
    }
    
    

    public Task createTask(Task task) {
        validateAndSetTaskFields(task);
        updateTaskStatus(task, LocalDate.now());
        return taskRepository.save(task);
    }

    public Task completeTask(int id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
        updateTaskStatus(task, LocalDate.now());
        return taskRepository.save(task);
    }

    public Task uncompleteTask(int id) {
        Task task = getTaskById(id);
        task.setCompleted(false);
        updateTaskStatus(task, LocalDate.now());
        return taskRepository.save(task);
    }

    public Task updateTask(int id, Task task) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        task.setId(id);
        validateAndSetTaskFields(task);
        updateTaskStatus(task, LocalDate.now());
        return taskRepository.save(task);
    }

    public void deleteTask(int id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    private void validateAndSetTaskFields(Task task) {
        if (task.getType() == null) {
            throw new IllegalArgumentException("Tipo de tarefa não especificado.");
        }

        if (task.getDescription() == null || task.getDescription().length() < 10) {
            throw new IllegalArgumentException("A descrição da tarefa deve possuir pelo menos 10 caracteres.");
        }

        if (task.getType() == TaskType.DATA) {
            if (task.getDueDate() == null || task.getDueDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("A data prevista de execução deve ser igual ou superior à data atual.");
            }
            task.setDueDays(null);
        } else if (task.getType() == TaskType.PRAZO) {
            if (task.getDueDays() == null || task.getDueDays() <= 0) {
                throw new IllegalArgumentException("O prazo previsto deve ser maior que zero.");
            }
            task.setDueDate(LocalDate.now());
        }
    }

    private void updateTaskStatus(Task task, LocalDate now) {
        switch (task.getType()) {
            case DATA:
                if (!task.isCompleted()) {
                    if (task.getDueDate().isBefore(now)) {
                        long daysLate = ChronoUnit.DAYS.between(task.getDueDate(), now);
                        task.setStatus(daysLate + " dias de atraso");
                    } else {
                        task.setStatus("Prevista");
                    }
                } else {
                    task.setStatus("Concluída");
                }
                break;
            case PRAZO:
                if (!task.isCompleted()) {
                    LocalDate dueDateWithExtension = task.getDueDate().plusDays(task.getDueDays());
                    if (now.isAfter(dueDateWithExtension)) {
                        long daysLate = ChronoUnit.DAYS.between(dueDateWithExtension, now);
                        task.setStatus(daysLate + " dias de atraso");
                    } else {
                        task.setStatus("Prevista");
                    }
                } else {
                    task.setStatus("Concluída");
                }
                break;
            case LIVRE:
                task.setStatus(task.isCompleted() ? "Concluída" : "Prevista");
                break;
            default:
                throw new IllegalArgumentException("Tipo de tarefa inválido: " + task.getType());
        }
    }
}
