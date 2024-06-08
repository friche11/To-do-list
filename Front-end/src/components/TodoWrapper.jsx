import React, { useState } from "react";
import { TodoForm } from "./TodoForm";
import { TodoList } from "./TodoList";

export const TodoWrapper = () => {
  const [todos, setTodos] = useState([
    { id: 1, description: "Tarefa exemplo", completed: true },
  ]);
  return (
    <div className="TodoWrapper">
      <h1>Lista de Tarefas</h1>
      <TodoForm />
      {todos.map((item) => (
        <TodoList key={item.id} task={item} />
      ))}
    </div>
  );
};
