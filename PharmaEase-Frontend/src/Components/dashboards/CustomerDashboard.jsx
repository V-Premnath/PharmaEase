import React, { useEffect, useState } from 'react';
import './CustomerDashboard.css';
import api from '../../services/axiosConfig';
import ProductSlider from '../ProductSlider';
import { useNavigate } from 'react-router-dom';

const CustomerDashboard = () => {

  const [pharmacies, setPharmacies] = useState([]);
  const [upload,setUpload] = useState(false);
  const navigate = useNavigate();

  const toggleUploadClick = () => {
    setUpload(!upload);
  }

  const handlePharmacyClick = (pharmacy_name) => {
    console.log(pharmacy_name);
    console.log('Pharmacy link:', `/${pharmacy_name}`);
    navigate(`/${pharmacy_name}`);
  }
  
  const fetchPharmacies = async () => {
    try {
      const response = await api.get('api/pharmacy/load-all');
      console.log(response.data); // Check the structure of the response
      if (!Array.isArray(response.data)) {
        console.error('Expected an array of pharmacies');
        return;
      }
      setPharmacies(response.data);
    } catch (error) {
      console.error('Failed to load pharmacies:', error);
    }
  };

  

  useEffect(() => {
    fetchPharmacies();
  }, []);

  return (
    <div className="customer-dashboard">
      <h1>Customer Dashboard</h1>
      <div className="pharmacy-selection">
        {pharmacies.map((pharmacy,index) => (
          <ProductSlider
            key={index}
            title={pharmacy}
            index={index}
            onClick={() => { handlePharmacyClick(pharmacy); }}
          />

        ))}
      </div>
      
      {
        upload && (<PrescriptionUpload toggleUploadClick={toggleUploadClick} />)
      }
      
    </div>
  );


}
export default CustomerDashboard;
