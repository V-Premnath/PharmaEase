import React, { useState } from "react";
import "./UploadMedicine.css";

const UploadMedicine = () => {
  const [formData, setFormData] = useState({
    name: "",
    genericName: "",
    composition: "",
    stock: "",
    cost: "",
    quantity: "",
    type: "",
    mfgDate: "",
    expDate: "",
    mfgBy: "",
    img: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Uploading:", formData);
    // Use API call here to submit formData
  };

  return (
    <div className="upload-medicine-container">
      <h2>Upload New Medicine</h2>
      <form className="medicine-form" onSubmit={handleSubmit}>
        {Object.entries(formData).map(([key, value]) => (
          <div className="form-group" key={key}>
            <label>{key.charAt(0).toUpperCase() + key.slice(1)}:</label>
            <input
              type={key.includes("Date") ? "date" : "text"}
              name={key}
              value={value}
              onChange={handleChange}
              required
            />
          </div>
        ))}
        <button type="submit" className="submit-btn">Submit</button>
      </form>
    </div>
  );
};

export default UploadMedicine;
