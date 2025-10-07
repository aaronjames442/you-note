import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL; // reads from .env file

export default {
  // 🔹 Log in user
  async login(credentials) {
    const response = await axios.post(`${API_URL}/auth/login`, credentials);
    const token = response.data.token;

    if (token) {
      localStorage.setItem("token", token); // save JWT
    }

    return response.data;
  },

  // 🔹 Register user
  async register(user) {
    return axios.post(`${API_URL}/auth/register`, user);
  },

  // 🔹 Logout user
  logout() {
    localStorage.removeItem("token");
  },

  // 🔹 Get JWT from storage
  getToken() {
    return localStorage.getItem("token");
  }
};
