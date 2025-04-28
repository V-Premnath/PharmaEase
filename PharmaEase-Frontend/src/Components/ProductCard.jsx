// ProductCard.jsx
import React from "react";
import { Link } from "react-router-dom";
import "./ProductCard.css";

const ProductCard = ({ medicine ,pharmacy_name}) => {
  console.log(medicine);
  if (!medicine) return null; // 👈 prevents destructure crash

  return (
    <Link to={`/${pharmacy_name}/medicine/${medicine.id}`}>
      <div className="product-card">
        <div className="card-img">
          <img src={medicine.img} alt={medicine.name} className="product-card-img" />
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
