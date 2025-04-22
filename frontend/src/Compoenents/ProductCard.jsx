// ProductCard.jsx
import React from "react";
import { Link } from "react-router-dom";
import "./ProductCard.css";

const ProductCard = ({ medicine }) => {
  console.log(medicine);
  if (!medicine) return null; // 👈 prevents destructure crash

  const {
    medicine_id,
    medicine_name,
    medicine_generic_name,
    medicine_type,
    medicine_cost,
    medicine_image_url,
    medicine_exp_date,
    medicine_stock,
  } = medicine;

  return (
    <Link to={`/ProductPage/${medicine.id}`}>
      <div className="product-card">
        <div className="card-img">
          <img src={medicine.img} alt={medicine_name} className="product-card-img" />
        </div>
        <div className="product-description">
          <p className="product-name">{medicine.name}</p>
          <p className="product-subtext">{medicine.genericName}</p>
          <p className="product-subtext">{medicine.type}</p>
          <p className="product-price">₹{medicine.cost}</p>
        </div>
      </div>
    </Link>
  );
};

export default ProductCard;
