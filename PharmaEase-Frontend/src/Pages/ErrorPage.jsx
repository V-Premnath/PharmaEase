import React from 'react';
import { useNavigate } from 'react-router-dom';
import './ErrorPage.css';

const ErrorPage = ({ errorCode = 404, message = "Page Not Found" }) => {
  const navigate = useNavigate();

  return (
    <div className="error-wrapper">
      <div className="error-card">
        <h1 className="error-code">{errorCode}</h1>
        <p className="error-message">{message}</p>
        <p className="error-description">
          The page you're looking for doesn’t exist or has been moved.
        </p>
        <button className="home-button" onClick={() => navigate("/")}>
          Go Home
        </button>
      </div>
    </div>
  );
};

export default ErrorPage;
