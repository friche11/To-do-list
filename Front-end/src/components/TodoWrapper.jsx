import React, { useState, useEffect } from "react";
import { TodoForm } from "./TodoForm";
import { TodoList } from "./TodoList";
import { getAllTasks, createTask, deleteTask, updateTask } from "../services/taskservice";

export const TodoWrapper = () => {
  const [todos, setTodos] = useState([]);

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const tasks = await getAllTasks();
        setTodos(tasks);
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };

    fetchTasks();
  }, []);

  const addTodo = async (description) => {
    try {
      const newTask = {
        description,
        completed: false,
        // Set default values for other fields as necessary
        dueDate: null,
        dueDays: 0,
        priority: "Low",
        status: "Pending",
        type: "General"
      };
      const createdTask = await createTask(newTask);
      setTodos([...todos, createdTask]);
    } catch (error) {
      console.error("Error creating task:", error);
    }
  };

  const deleteTodo = async (id) => {
    try {
      await deleteTask(id);
      setTodos(todos.filter((todo) => todo.id !== id));
    } catch (error) {
      console.error("Error deleting task:", error);
    }
  };

  const editTodo = async (id, updatedTask) => {
    try {
      const editedTask = await updateTask(id, updatedTask);
      setTodos(todos.map((todo) => (todo.id === id ? editedTask : todo)));
    } catch (error) {
      console.error("Error updating task:", error);
    }
  };

  return (
    <div className="TodoWrapper">
      <h1>Lista de Tarefas</h1>
      <TodoForm addTodo={addTodo} />
      {todos.map((todo) => (
        <TodoList
          key={todo.id}
          task={todo}
          deleteTodo={deleteTodo}
          editTodo={editTodo}
        />
      ))}
    </div>
  );
};