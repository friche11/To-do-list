import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheckSquare, faSquare, faTrash, faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import React, { useState, useEffect } from "react";
import { Modal } from "./modal";
import { getTaskById } from "../services/taskservice";

export const TodoList = ({ task, deleteTodo, editTodo, completeTodo, uncompleteTodo }) => {
  const [isDetailModalOpen, setIsDetailModalOpen] = useState(false);
  const [taskDetails, setTaskDetails] = useState({});

  useEffect(() => {
    if (isDetailModalOpen) {
      getTaskById(task.id).then((data) => {
        // Função para capitalizar a primeira letra de uma string
        const capitalizeFirstLetter = (str) => {
          return str.charAt(0).toUpperCase() + str.slice(1);
        };

        // Aplicando a capitalização aos campos necessários
        data.priority = capitalizeFirstLetter(data.priority.toLowerCase());
        data.type = capitalizeFirstLetter(data.type.toLowerCase());

        setTaskDetails(data);
      });
    }
  }, [isDetailModalOpen, task.id]);

  const formatDate = (dateString) => {
    const dateObj = new Date(dateString);
    const localDate = new Date(dateObj.getTime() + dateObj.getTimezoneOffset() * 60000);
    const day = localDate.getDate().toString().padStart(2, "0");
    const month = (localDate.getMonth() + 1).toString().padStart(2, "0");
    const year = localDate.getFullYear();
    return `${day}/${month}/${year}`;
  };

  return (
    <div className="Todo">
      <div className="checkbox-container" onClick={() => {
        if (task.completed) {
          uncompleteTodo(task.id);
        } else {
          completeTodo(task.id);
        }
      }}>
        <FontAwesomeIcon
          icon={task.completed ? faCheckSquare : faSquare}
          className="checkbox-icon"
        />
      </div>
      <p
        className={`${task.completed ? "completed" : "incompleted"}`}
        onClick={() => setIsDetailModalOpen(true)}
      >
        {task.description}
      </p>
      <div>
        {!task.completed && (
          <FontAwesomeIcon
            className="edit-icon"
            icon={faPenToSquare}
            onClick={() => editTodo(task)}
          />
        )}
        <FontAwesomeIcon
          className="delete-icon"
          icon={faTrash}
          onClick={() => deleteTodo(task.id)}
        />
      </div>
      <Modal isOpen={isDetailModalOpen} onClose={() => setIsDetailModalOpen(false)}>
        <div className="modal-header">
          <h2>Detalhes da Tarefa</h2>
        </div>
        <div className="modal-body">
          <p><strong>Descrição:</strong> {taskDetails.description}</p>
          <p><strong>Prioridade:</strong> {taskDetails.priority}</p>
          <p><strong>Tipo:</strong> {taskDetails.type}</p>
          {taskDetails.type === "Data" && (
            <p><strong>Data de vencimento:</strong> {formatDate(taskDetails.dueDate)}</p>
          )}
          {taskDetails.type === "Prazo" && (
            <p><strong>Data de criação:</strong> {formatDate(taskDetails.dueDate)}</p>
          )}
          {taskDetails.type === "Prazo" && (
            <p><strong>Dias de prazo:</strong> {taskDetails.dueDays}</p>
          )}
          <p><strong>Status:</strong> {taskDetails.status}</p>
        </div>
      </Modal>
    </div>
  );
};
