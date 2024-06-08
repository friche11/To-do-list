import React, { useState } from "react";
import { TodoForm } from "./TodoForm";
import { TodoList } from "./TodoList";
import { v4 as uuidv4 } from "uuid";

export const TodoWrapper = () => {
  const [todos, setTodos] = useState([
    { id: 1, description: "Tarefa exemplo", completed: false },
  ]);

  const addTodo = (description) => {
    setTodos([...todos, { id: uuidv4(), description: description, completed: false }]);
  };

  return (
    <div className="TodoWrapper">
      <h1>Lista de Tarefas</h1>
      <TodoForm addTodo={addTodo} />
      {todos.map((item) => (
        <TodoList key={item.id} task={item} />
      ))}
    </div>
  );
};
