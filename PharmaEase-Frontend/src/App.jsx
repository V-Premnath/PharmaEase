import "./App.css";
import Footer from "./Components/Footer";
import Header from "./Components/Header";
import { Routes, Route } from "react-router-dom";
import Home from "./Pages/Home";
import CartPage from "./Pages/CartPage";
import Signup from "./Pages/Signup";
import ProductPage from "./Pages/ProductPage";
// import Context from "./ContextApi/Context";
import OAuthRedirectHandler from "./Components/OAuthRedirectHandler";
import React, { useContext } from "react";
import Login from "./Pages/Login";
import Notifications from "./Components/Notifications";
import ErrorPage from "./Pages/ErrorPage";
import AuthContext from "./ContextApi/AuthContext";
import WaitingApproval from "./Pages/WaitingApproval";
import PharmacyPage from "./Pages/PharmacyPage";
function App() {

  const { isSignIn } = useContext(AuthContext);



  return (
    <>

      {isSignIn && <Header />}

      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/home" index element={<Home />} />
        <Route path="/CartPage" element={<CartPage />} />
        <Route path="/oauth2-redirect" element={<OAuthRedirectHandler />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/notifications" element={<Notifications />} />
        <Route path="/waiting-approval" element={<WaitingApproval />} />
        <Route path="/error" element={<ErrorPage errorCode={404} message="Oops! Page not found." />} />
        <Route path="/:pharmacy_name" element={<PharmacyPage />} />
        <Route path="/:pharmacy_name/medicine/:id" element={<ProductPage />} />
        <Route path="*" element={<ErrorPage errorCode={404} message="Oops! Page not found." />} />

        {/* <Route path=""/> */}

      </Routes>
      <Footer />
      {/* </Context> */}
    </>
  );
}

export default App;

