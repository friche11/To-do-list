import { faTrash } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";

export const TodoList = () => {
  return (
    <div className="Todo">
      <p className="completed">Tarefa 1</p>
      <div>
        <FontAwesomeIcon className="delete-icon" icon={faTrash} />
      </div>
    </div>
  );
};
