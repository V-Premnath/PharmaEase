import "./App.css";
import Footer from "./Compoenents/Footer";
import Header from "./Compoenents/Header";
import { Routes, Route } from "react-router-dom";
import Home from "./Pages/Home";
import CartPage from "./Pages/CartPage";
import Signup from "./Pages/Signup";
import ProductPage from "./Pages/ProductPage";
import Context from "./ContextApi/Context";
import OAuthRedirectHandler from "./Compoenents/OAuthRedirectHandler";
import React from "react";
import Login from "./Pages/Login";
import Notifications from "./Compoenents/Notifications";
function App() {
 
  
  return (
    <>
    <Context>
      
        <Routes>
          <Route path="/" element={<Login />}/>
          <Route path="/home" element={<Home />}/>
          <Route path="/login" element={<Login />}/>
          <Route path="/home" index element={<Home />}/>
          <Route path="/CartPage" element={<CartPage />}/>
          <Route path={`/ProductPage/:id`} element={<ProductPage />}/>
          <Route path="/oauth2-redirect" element={<OAuthRedirectHandler />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/notifications" element={<Notifications />} />

        </Routes>
      <Footer />
      </Context>
    </>
  );
}

export default App;

CartPage