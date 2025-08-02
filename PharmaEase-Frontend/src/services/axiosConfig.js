import axios from "axios";

// Create a new Axios instance
/**
 * Axios instance configured with the backend base URL.
 * The base URL is taken from the environment variable `BACKEND_BASE_URL` if available,
 * otherwise defaults to "http://localhost:8080".
 *
 * @type {import('axios').AxiosInstance}
 */
const api = axios.create({
  baseURL:  import.meta.env.VITE_BACKEND_BASE_URL || "http://localhost:8080",  // ✅ your backend base URL
});

// Automatically attach token from localStorage
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  config.headers['role'] = localStorage.getItem("role");
  config.headers['auth'] = localStorage.getItem("auth");
  config.headers['username'] = localStorage.getItem("username");
  config.headers['dbname'] = localStorage.getItem("dbname");
  config.headers['Content-Type'] = 'application/json';
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
},
  (error) => {
    return Promise.reject(error);
  });
api.interceptors.response.use(
  (response) =>{
    if (response.data.status === 'failure') {
      // Optional: Redirect or show error
      console.error('Failure status received', response.data);
      window.location.href = "/error";  // if you want
    }
    return response;
  },
  (error) => {

    if (error.response && error.response.status === 401) {
      // Optional: Clear localStorage or AuthContext
      localStorage.clear();

      // Redirect using JS
      window.location.href = "/error";
    }
    return Promise.reject(error);
  }
);


export default api;
