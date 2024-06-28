import React, { useState, useEffect } from "react";

export const TodoForm = ({ addTodo, taskToEdit, editTodo, onClose }) => {
  const [description, setDescription] = useState("");
  const [priority, setPriority] = useState("");
  const [type, setType] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [dueDays, setDueDays] = useState(0);
  const [isEditing, setIsEditing] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    if (taskToEdit) {
      setDescription(taskToEdit.description);
      setPriority(taskToEdit.priority);
      setType(taskToEdit.type);
      setDueDate(taskToEdit.dueDate || "");
      setDueDays(taskToEdit.dueDays || 0);
      setIsEditing(true);
    }
  }, [taskToEdit]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const newTask = { description, priority, type, dueDate, dueDays };

    try {
      // Validações no frontend antes de enviar para o backend
      if (!description || description.length < 10) {
        throw new Error("A descrição da tarefa deve possuir pelo menos 10 caracteres.");
      }

      if (!type) {
        throw new Error("Tipo da tarefa não especificado.");
      }

      if (!priority) {
        throw new Error("Prioridade da tarefa não especificada.");
      }

      if (type === "DATA" && (!dueDate || new Date(dueDate) < new Date())) {
        throw new Error("A data prevista deve ser igual ou superior à data atual.");
      }

      if (type === "PRAZO" && (dueDays === null || dueDays <= 0)) {
        throw new Error("O prazo previsto deve ser maior que zero.");
      }

      // Se estiver editando, chama a função editTodo
      if (isEditing) {
        await editTodo(taskToEdit.id, newTask);
        setIsEditing(false);
        onClose();
      } else {
        await addTodo(newTask);
      }

      // Limpar campos e mensagem de erro após submissão bem-sucedida
      setDescription("");
      setPriority("");
      setType("");
      setDueDate("");
      setDueDays(0);
      setErrorMessage(""); // Limpar mensagem de erro se houver

    } catch (error) {
      console.error("Error:", error.message);
      setErrorMessage(error.message); // Define a mensagem de erro capturada
    }
  };

  return (
    <form className="TodoForm" onSubmit={handleSubmit}>
      {errorMessage && <p className="error-message">{errorMessage}</p>}
      <input
        type="text"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        className="todo-input"
        placeholder="Descrição da Tarefa"
      />
      <select
        value={priority}
        onChange={(e) => setPriority(e.target.value)}
        className="todo-select"
      >
        <option value="" disabled>Prioridade</option>
        <option value="BAIXA">Baixa</option>
        <option value="MEDIA">Média</option>
        <option value="ALTA">Alta</option>
      </select>
      <select
        value={type}
        onChange={(e) => setType(e.target.value)}
        className="todo-select"
      >
        <option value="" disabled>Tipo</option>
        <option value="LIVRE">Livre</option>
        <option value="DATA">Data</option>
        <option value="PRAZO">Prazo</option>
      </select>
      {type === "DATA" && (
        <input
          type="date"
          value={dueDate}
          onChange={(e) => setDueDate(e.target.value)}
          className="todo-input-date"
        />
      )}
      {type === "PRAZO" && (
        <input
          type="number"
          value={dueDays}
          onChange={(e) => setDueDays(parseInt(e.target.value))}
          placeholder="Dias de Prazo"
          className="todo-input-number"
        />
      )}
      <button type="submit" className="todo-btn">
        {isEditing ? "Atualizar Tarefa" : "Adicionar Tarefa"}
      </button>
    </form>
  );
};
