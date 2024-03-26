DROP TABLE IF EXISTS task;
CREATE TABLE task (
id INT AUTO_INCREMENT PRIMARY KEY,
description VARCHAR(250) NOT NULL,
completed BOOLEAN
);
INSERT INTO task (description) VALUES
('Primeira tarefa'),
('Segunda tarefa'),
('Terceira tarefa');