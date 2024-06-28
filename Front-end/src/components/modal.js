
import React from 'react';
import ReactDOM from 'react-dom';

export const Modal = ({ isOpen, onClose, children }) => {
  if (!isOpen) return null;

  return ReactDOM.createPortal(
    <div className="modal">
      <div className="modal-content">
        <button onClick={onClose} className="modal-close">X</button>
        {children}
      </div>
    </div>,
    document.getElementById('modal-root')
  );
};
