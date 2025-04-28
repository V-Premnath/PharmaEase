// src/components/ProductSlider.js
import React, { useEffect, useState } from "react";
import Slider from "react-slick";
import ProductCard from "./ProductCard";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./ProductSlider.css";
import api from "../services/axiosConfig";
import { Link } from "react-router-dom";

const ProductSlider = ({ title , index}) => {
  const pharmacy_name = title;
  const [medicines, setMedicines] = useState([]);
  const fetchMedicines = async () => {
    try {
      const response = await api.get(`api/pharmacy/${pharmacy_name}/medicines`);
      console.log(response.data.data); // Check the structure of the response
      setMedicines(response.data.data); // Assuming the data is an array
    } catch (error) {
      console.error('Failed to load medicines:', error);
    }
  };


  useEffect(() => {
    const timer = setTimeout(() => {
      fetchMedicines();
    }, 500 * index); // delay based on slider index
    return () => clearTimeout(timer);
  }, []);

  const settings = {
    infinite: true,
    slidesToShow: 5,
    slidesToScroll: 0.5,
    autoplay: true,
    autoplaySpeed: 1200,
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
    <div className="slider-container" >
      <h2 className="slider-title"><Link to={`/${pharmacy_name}`} >{title}</Link></h2>{medicines.length === 0 ? (
        <p>No medicines found for {title}</p>
      ) : (
        <Slider {...settings}>
          {medicines.map((medicine, index) =>
            medicine ? (
              <ProductCard key={medicine.id || index} medicine={medicine} pharmacy_name={pharmacy_name} />
            ) : null
          )}

        </Slider>
      )}


    </div>
  );
};

export default ProductSlider;
