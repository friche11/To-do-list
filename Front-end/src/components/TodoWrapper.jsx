import React, { useState, useEffect } from "react";
import { TodoForm } from "./TodoForm";
import { TodoList } from "./TodoList";
import { getAllTasks, createTask, deleteTask, updateTask, completeTask, uncompleteTask } from "../services/taskservice";
import { Modal } from "./modal";

export const TodoWrapper = () => {
  const [todos, setTodos] = useState([]);
  const [taskToEdit, setTaskToEdit] = useState(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);

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

  const addTodo = async (newTask) => {
    try {
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
      setTaskToEdit(null);
    } catch (error) {
      console.error("Error updating task:", error);
    }
  };

  const completeTodo = async (id) => {
    try {
      await completeTask(id); // Chamando a função do serviço para completar a tarefa
      const updatedTodos = todos.map((todo) =>
        todo.id === id ? { ...todo, completed: true } : todo
      );
      setTodos(updatedTodos);
    } catch (error) {
      console.error("Error completing task:", error);
    }
  };

  const uncompleteTodo = async (id) => {
    try {
      await uncompleteTask(id); // Chamando a função do serviço para desmarcar a tarefa
      const updatedTodos = todos.map((todo) =>
        todo.id === id ? { ...todo, completed: false } : todo
      );
      setTodos(updatedTodos);
    } catch (error) {
      console.error("Error uncompleting task:", error);
    }
  };

  const startEditTodo = (task) => {
    setTaskToEdit(task);
    setIsEditModalOpen(true);
  };

  return (
    <div className="TodoWrapper">
      <h1>Lista de Tarefas</h1>
      <Modal isOpen={isEditModalOpen} onClose={() => setIsEditModalOpen(false)}>
        <TodoForm
          addTodo={addTodo}
          taskToEdit={taskToEdit}
          editTodo={editTodo}
          onClose={() => setIsEditModalOpen(false)}
        />
      </Modal>
      <TodoForm addTodo={addTodo} />
      {todos.map((todo) => (
        <TodoList
          key={todo.id}
          task={todo}
          deleteTodo={deleteTodo}
          editTodo={startEditTodo}
          completeTodo={completeTodo}
          uncompleteTodo={uncompleteTodo} // Adicione a função uncompleteTodo ao componente TodoList
        />
      ))}
    </div>
  );
};
