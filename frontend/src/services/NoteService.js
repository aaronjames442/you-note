import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL; // from .env file

// Helper to add token automatically
function authHeader() {
  const token = localStorage.getItem("token");
  return token ? { Authorization: `Bearer ${token}` } : {};
}

export default {
  // Get all notes
  getAllNotes() {
    return axios.get(`${API_URL}/notes`, { headers: authHeader() });
  },

  // Get note by id
  getNoteById(id) {
    return axios.get(`${API_URL}/notes/${id}`, { headers: authHeader() });
  },

  // Create a new note
  createNote(note) {
    return axios.post(`${API_URL}/notes`, note, { headers: authHeader() });
  },

  // Update a note
  updateNote(id, note) {
    return axios.put(`${API_URL}/notes/${id}`, note, { headers: authHeader() });
  },

  // Delete a note
  deleteNote(id) {
    return axios.delete(`${API_URL}/notes/${id}`, { headers: authHeader() });
  }
};
