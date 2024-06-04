package com.labdessoft.roteiro01.Integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import com.labdessoft.roteiro01.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;


import com.labdessoft.roteiro01.Roteiro01Application;
import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.enums.TaskPriority;
import com.labdessoft.roteiro01.enums.TaskType;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@SpringBootTest(classes = {Roteiro01Application.class}, webEnvironment
= SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class TaskControllerIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    private int taskId; 

@Before
public void setup() {
RestAssured.baseURI = "http://localhost:8080";
RestAssured.port = 8080;

}

    @Test
    public void
    givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
    get("/api/tasks").then().statusCode(200);
    }

    @Test
    public void givenUrl_whenSuccessOnPostCreatesTask_thenCorrect() {
        // Criar a tarefa e salvar no banco de dados
        Task task = new Task();
        task.setDescription("Tarefa para testes");
        task.setCompleted(false);
        task.setDueDate(LocalDate.of(2024, 5, 10));
        task.setDueDays(5);
        task.setType(TaskType.PRAZO);
        task.setPriority(TaskPriority.MEDIA);

        taskId = task.getId();
    
        given()
            .contentType(ContentType.JSON)
            .body(task)
        .when()
            .post("/api/create/task")
        .then()
            .statusCode(201);
    }

    
    @Test
    public void
    givenUrl_whenSuccessOnGetsResponseAndJsonHasOneTask_thenCorrect() {
    Optional<Task> optionalTask = taskRepository.findById(taskId);
    if (optionalTask.isPresent()){
        when()
        .get("/api/task/" + taskId)
        .then()
        .statusCode(200);
    }
    }
    

    @Test
    public void givenUrl_whenSuccessOnPutCompletesTask_thenCorrect() {
        when().put("/api/complete/1")
            .then().statusCode(200);
    }

    @Test
    public void givenUrl_whenSuccessOnPutUpdatesTask_thenCorrect() {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task updatedTask = optionalTask.get();
            updatedTask.setDescription("Tarefa atualizada");
            updatedTask.setId(taskId);
    
            given()
                .contentType(ContentType.JSON)
                .body(updatedTask)
            .when()
                .put("/api/edit/" + taskId)
            .then()
                .statusCode(200);
        }
    }
    

    @Test
    public void givenUrl_whenFailureOnGetTaskById_thenNotFound() {
        when().get("/api/task/9999")
            .then().statusCode(404);
    }

    @Test
    public void givenUrl_whenSuccessOnDeleteRemovesTask_thenCorrect() {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
        when()
            .delete("/api/delete/" + taskId)
        .then()
            .statusCode(204);
        }
    }

    
}
