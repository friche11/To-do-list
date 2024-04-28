package com.labdessoft.roteiro01.entity;

import com.labdessoft.roteiro01.enums.TaskPriority;
// Importando a classe TaskType do pacote correto
import com.labdessoft.roteiro01.enums.TaskType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Todos os detalhes sobre uma tarefa.")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    private String description;

    @Schema(description = "Tarefa concluída ou não concluída")
    private Boolean completed;

    @Schema(description = "Vencimento da tarefa em data")
    private LocalDate dueDate;

    @Schema(description = "Vencimento da tarefa em dias")
    private Integer dueDays;

    @Schema(description = "Tipo da tarefa - Data, prazo ou livre -")
    private TaskType type;

    @Schema(description = "Status da tarefa - Prevista, X dias de atraso ou concluída -")
    private String status;

    @Schema(description = "Prioridade da tarefa - Alta, média, baixa -")
    private TaskPriority priority;

    public Task(String description, Boolean completed, LocalDate dueDate, Integer dueDays, TaskType type, String status, TaskPriority priority) {
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
        this.dueDays = dueDays;
        this.type = type;
        this.status = status;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getDueDays() {
        return dueDays;
    }

    public void setDueDays(Integer dueDays) {
        this.dueDays = dueDays;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

   
    @Override
    public String toString() {
        return "Task [id=" + id + ", description=" + description + ", completed=" + completed + ", status=" + status + ", priority=" + priority + "]";
    }

}
