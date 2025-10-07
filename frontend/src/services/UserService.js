import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL 

export default {
  getUserProfile(id) {
    return axios.get(`${API_URL}/${id}`);
  },
  getAllUsers() {
    return axios.get(API_URL);
  }
};
