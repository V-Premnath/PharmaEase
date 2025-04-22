import axios from "axios";

// Create a new Axios instance
const api = axios.create({
  baseURL: "http://localhost:8080", // ✅ your backend base URL
});

// Automatically attach token from localStorage
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  config.headers['role'] = localStorage.getItem("role");
  config.headers['auth'] = localStorage.getItem("auth");
  config.headers['username'] = localStorage.getItem("username");
  config.headers['Content-Type'] = 'application/json';  
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
},
(error) => {
  return Promise.reject(error);
});

export default api;
