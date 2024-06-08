import React from "react";
export const TodoList = ({ task }) => {
  return (
    <div className="Todo">
      <p className={`${task.completed ? "completed" : "incompleted"}`}>
        {task.description}
      </p>
    </div>
  );
};
