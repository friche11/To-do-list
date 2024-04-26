package com.labdessoft.roteiro01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.repository.TaskRepository;
import java.time.LocalDate;
import java.util.List;
import java.time.temporal.ChronoUnit;

// Importando a classe TaskType do pacote correto
import com.labdessoft.roteiro01.enums.TaskType;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        LocalDate now = LocalDate.now();
        for (Task task : tasks) {
            updateTaskStatus(task, now);
        }
        return tasks;
    }

    public Task getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        updateTaskStatus(task, LocalDate.now());
        return task;
    }

    public Task createTask(Task task) {
        // Verifica se o tipo de tarefa é especificado
        if (task.getType() == null) {
            throw new IllegalArgumentException("Tipo de tarefa não especificado.");
        }
        
        // Verifica se a tarefa do tipo "Data" tem a data prevista de execução correta
        if (task.getType() == TaskType.DATA && task.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data prevista de execução deve ser igual ou superior à data atual.");
        }
        
        // Verifica se a tarefa do tipo "Prazo" tem o prazo previsto válido
        if (task.getType() == TaskType.PRAZO && (task.getDueDays() == null || task.getDueDays() <= 0)) {
            throw new IllegalArgumentException("O prazo previsto deve ser maior que zero.");
        }
    
        return taskRepository.save(task);
    }
    

    public Task completeTask(Long id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        task.setId(id);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
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
                    if (dueDateWithExtension.isBefore(now)) {
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
