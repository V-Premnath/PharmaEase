// src/Pages/WaitingApproval.js

import React from "react";
import { Typography, CircularProgress, Box } from "@mui/material";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./WaitingApproval.css";
import api from "../services/axiosConfig";

const WaitingApproval = () => {
  const [email, setEmail] = React.useState("");
  const navigate = useNavigate();
  
  useEffect( () => {
      // Extract token and role from URL params
      const params = new URLSearchParams(window.location.search);
      setEmail( params.get("email"));
      const timer = setTimeout(() => {
        navigate("/login");
      }, 5000);
    
      // Clean up the timer if component unmounts
      return () => clearTimeout(timer);
  } ,[] );
  
  const handleWithdraw = async (email) => {
    try {
      const response = await api.get(`http://localhost:8080/api/user/revoke-approval-request?email=${email}`);
      if (response.status === 200) {
        // Handle success
        alert("Request withdrawn successfully");
        navigate("/login");
      } else {
        alert("Failed to withdraw request");
      }
    } catch (error) {
      console.error("Error:", error);
    }finally{
      console.log("Trying to withdraw request for:", email);

    }
  }


  return (<>
    <Box
      sx={{
        minHeight: "100vh",
        backgroundColor: "#f0fdf4",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        textAlign: "center",
        padding: "2rem",
      }}
    >
      <CircularProgress sx={{ color: "#0f766e", mb: 3 }} thickness={5} size={60} />
      <Typography variant="h4" gutterBottom color="#0f766e">
        Thank You for Registering!
      </Typography>
      <Typography variant="body1" color="textSecondary" maxWidth="sm">
        Your account has been created successfully.  
        Please wait while a pharmacy admin reviews and approves your account.
        You will be notified once your access is granted.
      </Typography>
      {<button className="disapprove-btn"  onClick={() => handleWithdraw(email)} >
     <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
       <line x1="18" y1="6" x2="6" y2="18"></line>
       <line x1="6" y1="6" x2="18" y2="18"></line>
     </svg>
    Withdraw request
   </button>}
    </Box>
     
   </>
  );
};

export default WaitingApproval;
