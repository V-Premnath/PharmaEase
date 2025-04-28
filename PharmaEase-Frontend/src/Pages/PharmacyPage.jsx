import React, { useEffect, useState } from 'react';
import './PharmacyPage.css';
import { useParams } from 'react-router-dom';
import api from '../services/axiosConfig';
import ProductCard from '../Components/ProductCard';

const PharmacyPage = () => {
  const { pharmacy_name } = useParams();
  const [medicines, setMedicines] = useState([]);
  const [loading, setLoading] = useState(true);
  
  useEffect(() => {
    api.get(`/api/pharmacy/${pharmacy_name}/medicines`)
      .then(response => {
        console.log("Fetched medicines:", response.data);
        const result = response.data.data;
        if (Array.isArray(result)) {
          setMedicines(result);
        } else {
          console.error("Expected array but got:", result);
        }
      })
      .catch(error => {
        console.error('Error fetching medicines:', error);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  return (
    <div className="customer-dashboard">
      <header className="dashboard-header" style={{ backgroundColor: '#002F40' ,color: 'white',paddingTop: '25px',marginTop:'50px' }}>
        <h1>Welcome to {pharmacy_name}</h1>
      </header>
     
      <section className="medicine-grid">
        {loading ? (
          <p className="loading-text">Loading medicines...</p>
        ) : medicines.length === 0 ? (
          <p className="loading-text">No medicines available</p>
        ) : (
          medicines.map((medicine) => (
            <ProductCard key={medicine.id} medicine={medicine} pharmacy_name={pharmacy_name} />
          ))
        )}
      </section>

      
      
    </div>
  );
};
export  default PharmacyPage;