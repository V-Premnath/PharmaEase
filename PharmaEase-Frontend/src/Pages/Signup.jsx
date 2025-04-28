import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import styles from './signup.module.css';
import axios from 'axios';

const SuccessMessage = () => (
  <div className={styles.success}>
    ✅ Registration Successful!
  </div>
);

const FailureMessage = () => (
  <div className={styles.failure}>
    ❌ Registration Failed. Please try again.
  </div>
);

const Signup = () => {
  const navigate = useNavigate(); // ✅ Correct place to use hook

  const [formData, setFormData] = useState({
    username: '',
    role: '',
    pharmacyName: '',
    password: '',
    address: '',
    useremail: '',
  });

  const [email, setEmail] = useState('');
  const [submitted, setSubmitted] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const location = useLocation();

  useEffect(() => {
    const query = new URLSearchParams(location.search);
    const emailFromURL = query.get("email");
    if (emailFromURL) {
      setEmail(emailFromURL);
    }
    else{
      navigate('/login');
    }
  }, [location]);

  const handleChange = async (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));

    if (name === "username") {
      try {
        const response = await axios.post('http://localhost:8080/auth/check-username', {
          username: value
        });
        if (response.status === 400) {
          setError("Username already exists");
        } else {
          setError("");
        }
      } catch (err) {
        setError("Username already exists or server error");
      }
    }
    if (name === "pharmacyName") {
      try {
        const response = await axios.post('http://localhost:8080/auth/check-pharmacy-name', {
          pharmacyName: value
        });
        if (response.status === 400) {
          setError("Pharmacy already exists");
        } else {
          setError("");
        }
      } catch (err) {
        setError("Pharmacy does not exists or server error");
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/auth/signup', {
        ...formData,
        email,
      });

      if (response.status === 200 || response.status === 201) {
        setIsSuccess(true);
        setSubmitted(true);
        navigate(`/waiting-approval?email=${email}`); // ✅ useNavigate instead of window()
      } else {
        setIsSuccess(false);
        setSubmitted(true);
      }
    } catch (error) {
      console.error("Error during registration:", error);
      setIsSuccess(false);
      setSubmitted(true);
    }
  };

  return (
    <div className={styles.wrapper}>
      <form className={styles.form} onSubmit={handleSubmit} method='POST'>
        <h2 className={styles.heading}>User Registration</h2>

        <div className={styles.field}>
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            name="username"
            value={formData.username}
            onChange={handleChange}
            required
            placeholder="Enter username"
          />
        </div>

        <div className={styles.field}>
          <label htmlFor="useremail">Email</label>
          <input
            type="text"
            id="useremail"
            name="useremail"
            value={email}
            readOnly
          />
        </div>

        <div className={styles.field}>
          <label htmlFor="role">Role</label>
          <select
            id="role"
            name="role"
            value={formData.role}
            onChange={handleChange}
            required
          >
            <option value="">Select role</option>
            <option value="DRUGGIST">Druggist</option>
            <option value="PHARMACY_ADMIN">Pharmacy Admin</option>
            <option value="CUSTOMER">Customer</option>
            <option value="VENDOR">Vendor</option>
          </select>
        </div>

        {(formData.role === 'DRUGGIST' || formData.role === 'PHARMACY_ADMIN') && (
          <div className={styles.field}>
            <label htmlFor="pharmacyName">Pharmacy Name</label>
            <input
              type="text"
              id="pharmacyName"
              name="pharmacyName"
              value={formData.pharmacyName}
              onChange={handleChange}
              required
              placeholder="Enter pharmacy name"
            />
          </div>
        )}

        {formData.role === 'PHARMACY_ADMIN' && (
          <>
            <div className={styles.field}>
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                required
                placeholder="Create a password"
              />
            </div>
            <div className={styles.field}>
              <label htmlFor="text">Address</label>
              <input
                type="text"
                id="address"
                name="address"
                value={formData.address}
                onChange={handleChange}
                required
                placeholder="Enter address"
              />
            </div>
          </>
        )}

        <button type="submit" className={styles.button} disabled={error && role === "DRUGGIST"}>
          Register
        </button>

        <div className={styles.error}>
          {error && <p>{error}</p>}
        </div>
      </form>

      {/* Conditional rendering after submission */}
      {submitted && (isSuccess ? <SuccessMessage /> : <FailureMessage />)}
    </div>
  );
};

export default Signup;
