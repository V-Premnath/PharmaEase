// context/AuthContext.js
import React, { createContext, useState, useEffect } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isSignIn, setIsSignIn] = useState(!!localStorage.getItem("token"));
  // Check if the user is signed in based on the presence of a token

  useEffect(() => {
    // Auto-refresh on token change
    const token = localStorage.getItem("token");
    setIsSignIn(!!token);
  }, []);

  return (
    <AuthContext.Provider value={{ isSignIn, setIsSignIn }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
