// pages/OAuthRedirectHandler.js
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../ContextApi/AuthContext";
import { useContext } from "react";

const OAuthRedirectHandler = () => {
  const navigate = useNavigate();
  const { setIsSignIn } = useContext(AuthContext);

  useEffect(() => {
    // Extract token and role from URL params
    const params = new URLSearchParams(window.location.search);
    const token = params.get("token");
    const role = params.get("role");
    const username = params.get("username");
    const dbname = params.get("dbname");
    const token1 = localStorage.getItem("token");
    const role1 = localStorage.getItem("role");

    if (token1 && role1){
      navigate("/home");
      console.log("1 . Token and role set in local storage");
    }
    else {
      console.error("No token or role in OAuth redirect");
      navigate("/login"); // Redirect to home or an error page
    //  console.log("Token and role set in local storage");
    }
    if ((token && role) ) {
      localStorage.setItem("token", token);
      localStorage.setItem("role", role);
      localStorage.setItem("auth", "true");
      localStorage.setItem("username", username);
      localStorage.setItem("dbname", dbname);
      setIsSignIn(true);
      navigate("/home"); // or whatever route makes sense
      console.log("2. . Token and role set in local storage");
    } else {
          console.error("No token or role in OAuth redirect");
      navigate("/login"); // Redirect to home or an error page
    }
  }, [navigate]);

  return <p>Signing you in...</p>;
};

export default OAuthRedirectHandler;
