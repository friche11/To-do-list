import axios from 'axios';

const API_URL = 'http://localhost:8080/api'; // Altere para a URL da sua API

export const getAllTasks = async () => {
    try {
        const response = await axios.get(`${API_URL}/tasks`);
        return response.data;
    } catch (error) {
        console.error("There was an error fetching the tasks!", error);
        throw error;
    }
};

export const createTask = async (task) => {
    try {
        const response = await axios.post(`${API_URL}/create/task`, task);
        return response.data;
    } catch (error) {
        console.error("There was an error creating the task!", error);
        throw error;
    }
};

export const deleteTask = async (id) => {
    try {
        await axios.delete(`${API_URL}/delete/${id}`);
    } catch (error) {
        console.error(`There was an error deleting the task with ID: ${id}`, error);
        throw error;
    }
};

export const updateTask = async (id, task) => {
    try {
        const response = await axios.put(`${API_URL}/edit/${id}`, task);
        return response.data;
    } catch (error) {
        console.error(`There was an error updating the task with ID: ${id}`, error);
        throw error;
    }
};
