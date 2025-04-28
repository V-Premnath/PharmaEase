import React, { useState } from "react";
import "./VendorDashboard.css";
import UploadMedicine from "../UploadMedicine";

const VendorDashboard = () => {
  const [showUpload, setShowUpload] = useState(false);

  const scrollToUpload = () => {
    setShowUpload(true);
    setTimeout(() => {
      const uploadSection = document.getElementById("upload-section");
      if (uploadSection) uploadSection.scrollIntoView({ behavior: "smooth" });
    }, 100);
  };

  return (
    <div className="vendor-dashboard-container">
      <header className="vendor-header">
        <h1>Vendor Dashboard</h1>
      </header>

      <section className="policy-section">
        <h2>PharmaEase Vendor Guidelines</h2>
        <ul>
          <li>Ensure all medicines are authentic and approved by drug authorities.</li>
          <li>Provide accurate expiry, composition, and manufacturing details.</li>
          <li>Do not sell expired or banned drugs under any circumstances.</li>
          <li>Respond to stock requests and replacement queries promptly.</li>
          <li>Violating any policy may result in account suspension.</li>
        </ul>
      </section>

      <div className="scroll-button-container">
        <button className="upload-button" onClick={scrollToUpload}>
          Upload New Medicine
        </button>
      </div>

      <div id="upload-section">
        {showUpload && <UploadMedicine />}
      </div>
    </div>
  );
};

export default VendorDashboard;
