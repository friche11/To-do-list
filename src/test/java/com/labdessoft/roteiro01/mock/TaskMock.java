package com.labdessoft.roteiro01.mock;

import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.enums.TaskType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TaskMock {

    public static List<Task> createTasks() {
        Task task1 = new Task();
        task1.setId(1);
        task1.setDescription("Task 1");
        task1.setType(TaskType.DATA);
        task1.setDueDate(LocalDate.now().plusDays(5));

        Task task2 = new Task();
        task2.setId(2);
        task2.setDescription("Task 2");
        task2.setType(TaskType.PRAZO);
        task2.setDueDate(LocalDate.now().plusDays(10));
        task2.setDueDays(7);

        return Arrays.asList(task1, task2);
    }

    public static Task createTask(int id) {
        Task task = new Task();
        task.setId(id);
        task.setDescription("Task" + id +"");
        task.setType(TaskType.DATA);
        task.setDueDate(LocalDate.now().plusDays(5));
        return task;
    }
}
