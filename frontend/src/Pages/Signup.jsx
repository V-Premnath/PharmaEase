import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
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
  const [error, setError] = useState(true);

  const location = useLocation();

  useEffect(() => {
    const query = new URLSearchParams(location.search);
    const emailFromURL = query.get("email");
    if (emailFromURL) {
      setEmail(emailFromURL);
    }
  }, [location]);

  const handleChange = async (e) => {
    const { name, value } = e.target;
    try{
      const response = await axios.post('http://localhost:8080/auth/check-username',
                                         {"username":formData.username });
      if (response.status === 400) {
        setError("Username already exists");
      }
    }
    catch(err){
      setError(err);
    }
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Mock success/failure condition (replace with real validation/API call)
  

    try {
      console.log("Form Data Before  Submission:", {
        ...formData,
        email,
      });
        const response = await axios.post('http://localhost:8080/auth/signup', {
          ...formData,
          email,
        });

      if (response.status === 200 || response.status === 201) {
        console.log("Form Data Submitted:", {
          ...formData,
          email,
        });
        setIsSuccess(true);
      } else {
        setIsSuccess(false);
      }
    } catch (error) {
      console.error("Error during registration:", error);
      setIsSuccess(false);
    }


    // Optional: Alert or log form

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

        {/* Pharmacy Name field only for DRUGGIST or PHARMACY_ADMIN */}
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

        {/* Password field only for PHARMACY_ADMIN */}
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

        <button type="submit" className={styles.button}>
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
