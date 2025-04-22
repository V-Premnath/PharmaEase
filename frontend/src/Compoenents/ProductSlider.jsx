// src/components/ProductSlider.js
import React, { useEffect, useState } from "react";
import Slider from "react-slick";
import axios from "axios";
import ProductCard from "./ProductCard";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./ProductSlider.css";

const ProductSlider = ({ title }) => {
  const [medicines, setMedicines] = useState([]);
  const fetchMedicines = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/pharmacy/customer/medicines');
      console.log(response.data.data); // Check the structure of the response
      setMedicines(response.data.data); // Assuming the data is an array
    } catch (error) {
      console.error('Failed to load medicines:', error);
    }
  };
  

  useEffect(() => {
    fetchMedicines();
  }, []);

  const settings = {
    infinite: true,
    slidesToShow: 4,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2500,
    responsive: [
      {
        breakpoint: 1024,
        settings: { slidesToShow: 3 },
      },
      {
        breakpoint: 600,
        settings: { slidesToShow: 2 },
      },
      {
        breakpoint: 480,
        settings: { slidesToShow: 1 },
      },
    ],
  };
  if (!Array.isArray(medicines)) {
    return <p>Loading or no medicines available.</p>;
  }

  return (
    <div className="slider-container">
      <h2 className="slider-title">{title}</h2>
      <Slider {...settings}>
      {medicines.map((medicine, index) =>
  medicine ? (
    <ProductCard key={medicine.medicine_id || index} medicine={medicine} />
  ) : null
)}

      </Slider>
    </div>
  );
};

export default ProductSlider;
