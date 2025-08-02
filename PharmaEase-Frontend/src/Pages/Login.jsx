import { Avatar, Button, Paper, Typography } from "@mui/material";
import { useEffect } from "react";
import './Login.css'; // Import the CSS file
import { useNavigate } from "react-router-dom";
import api from "../services/axiosConfig";

const Login = () => {
  const navigate  =  useNavigate();
  useEffect(() => {
    const token1 = localStorage.getItem("token");
  const role1 = localStorage.getItem("role");

  if (token1 && role1) {
    navigate("/home");
  }
  }, []);
  

  const handleLogin = () => {
    window.location.href = `${api.defaults.baseURL}/oauth2/authorization/google`;
  };

  return (
    <div className="login-root">
      <Paper elevation={6} className="login-card">
        <Avatar className="login-avatar">P</Avatar>
        <Typography variant="h5" className="login-heading">
          Welcome to PharmaEase
        </Typography>
        <Typography variant="body2" className="login-subtext">
          Please login to continue
        </Typography>
        <Button
          variant="contained"
          fullWidth
          onClick={handleLogin}
          className="login-button"
        >
          Login with Google
        </Button>
      </Paper>
    </div>
  );
};

export default Login;
