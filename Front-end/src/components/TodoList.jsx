import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash, faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import React from "react";

export const TodoList = ({ task }) => {
  return (
    <div className="Todo">
      <p className={`${task.completed ? "completed" : "incompleted"}`}>
        {task.description}
      </p>
      <div>
        <FontAwesomeIcon className="edit-icon" icon={faPenToSquare} />
        <FontAwesomeIcon className="delete-icon" icon={faTrash} />
      </div>
    </div>
  );
};
