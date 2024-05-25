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


// Importando a classe TaskType do pacote correto
import com.labdessoft.roteiro01.enums.TaskType;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Page<Task> listAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks;
    }

    public Task getTaskById(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        return task;
    }

    public Task createTask(Task task) {
        // Verifica se o tipo de tarefa é especificado

        if (task.getType() == null) {
            throw new IllegalArgumentException("Tipo de tarefa não especificado.");
        }

        // Verifica se a descrição da tarefa possui pelo menos 10 caracteres
    if (task.getDescription() == null || task.getDescription().length() < 10) {
        throw new IllegalArgumentException("A descrição da tarefa deve possuir pelo menos 10 caracteres.");
    }
        
        // Verifica se a tarefa do tipo "Data" tem a data prevista de execução correta
        if (task.getType() == TaskType.DATA && task.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data prevista de execução deve ser igual ou superior à data atual.");
        }
        
        // Verifica se a tarefa do tipo "Prazo" tem o prazo previsto válido
        if (task.getType() == TaskType.PRAZO && (task.getDueDays() == null || task.getDueDays() <= 0)) {
            throw new IllegalArgumentException("O prazo previsto deve ser maior que zero.");
        }
        updateTaskStatus(task, LocalDate.now());
        return taskRepository.save(task);
    }
    

    public Task completeTask(int id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
        updateTaskStatus(task, LocalDate.now());
        return taskRepository.save(task);
    }

    public Task updateTask(int id, Task task) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        task.setId(id);
        updateTaskStatus(task, LocalDate.now());
        return taskRepository.save(task);
    }
    

    public void deleteTask(int id) {
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
                    LocalDate dueDateWithExtension = task.getDueDate().plusDays(task.getDueDays()); // Adicionando os dias de prazo à data de vencimento original
                    if (now.isAfter(dueDateWithExtension)) { // Verifica se a data atual está após a data com a extensão
                        long daysLate = ChronoUnit.DAYS.between(dueDateWithExtension, now); // Calcula os dias de atraso
                        task.setStatus(daysLate + " dias de atraso"); // Atualiza o status para refletir o atraso
                    } else {
                        task.setStatus("Prevista"); // Atualiza o status como prevista se ainda não estiver atrasada
                    }
                } else {
                    task.setStatus("Concluída"); // Atualiza o status como concluída se a tarefa estiver completa
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
