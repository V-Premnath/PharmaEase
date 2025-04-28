import React, { useState } from 'react';
import './PrescriptionUpload.css';

const PrescriptionUpload = ({ toggleUploadClick }) => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [preview, setPreview] = useState(null);

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setSelectedFile(file);
    if (file) {
      setPreview(URL.createObjectURL(file));
    }
  };

  const handleUpload = () => {
    if (!selectedFile) {
      alert("Please select a prescription to upload.");
      return;
    }
    // Implement your upload logic here
    alert("Prescription uploaded successfully!");
  };

  return (
    <div className="upload-container">

      <button className="back-btn" onClick={() => {

window.scrollTo({ top:1, behavior: 'smooth' });
        toggleUploadClick();

      }}>← Back</button>
      <div className="paper">
        <h2 className="upload-title">Upload Your Prescription</h2>
        <input
          type="file"
          accept="image/*,.pdf"
          onChange={handleFileChange}
          className="file-input"
        />
        {preview && (
          <div className="preview">
            <img src={preview} alt="Preview" />
          </div>
        )}
        <button onClick={handleUpload} className="upload-btn">
          Upload
        </button>
      </div>
    </div>
  );
};

export default PrescriptionUpload;
