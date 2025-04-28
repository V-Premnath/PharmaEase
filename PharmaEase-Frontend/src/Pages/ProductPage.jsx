import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../services/axiosConfig'; // Axios instance
import './ProductPaga.css';

const ProductPage = () => {
  const { id } = useParams(); // gets medicine ID from the route
  const {pharmacy_name } = useParams(); // gets pharmacy name from the route
  const [medicine, setMedicine] = useState(null);
  const navigate = useNavigate();

  const addToCart = async () => {
    api.post('/api/cart/add-to-cart', {
      medicineId: id,
      pharmacyDBName: pharmacy_name,
    })
      .then(res => {
        console.log("Added to cart: ", res.data);
        alert("Added to cart");
      })
      .catch(err => {
        console.error('Error adding to cart:', err);
        alert("Error adding to cart");
      });
  }

  useEffect(() => {
    console.log("Pharmacy Name: ", pharmacy_name);
    console.log("Medicine ID: ", id);
    api.get(`/api/pharmacy/${pharmacy_name}/medicine/${id}`)
      .then(res => {
        console.log("DATA : " ,res.data);
        setMedicine(res.data);
        
      })
      .catch(err => {
        console.error('Error fetching product:', err);
        
      });
  }, [id, navigate]);

  useEffect(() => {
    if (medicine) {
      console.log("Updated medicine state:", medicine);
    }
  }, [medicine]);

  if (!medicine) {
    return <div className="product-loading">Loading...</div>;
  }

  return (
    <div className="product-page" style={{ paddingTop: "90px" }}>
      <button className="back-btn" onClick={() => navigate(-1)}>← Back</button>

      <div className="product-container">
        <img
          src={medicine.img }
          alt={medicine.name}
          className="product-image"
        />
  
        <div className="product-details">
          <h2>{medicine.name}</h2>
          <p><strong>Generic:</strong> {medicine.genericName}</p>
          <p><strong>Composition:</strong> {medicine.composition}</p>
          <p><strong>Manufacturer:</strong> {medicine.mfgBy}</p>
          <p><strong>Price:</strong> ₹{medicine.cost}</p>
          <p><strong>Expiry Date:</strong> {medicine.expDate}</p>
          <p><strong>Type:</strong> {medicine.type}</p>
          <button className="add-to-cart-btn" onClick={addToCart}>Add to Cart</button>
        </div>
      </div>
    </div>
  );
};

export default ProductPage;
