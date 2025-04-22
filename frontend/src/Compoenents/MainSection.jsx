import { useEffect, useState } from "react";
import SuperAdminDashboard from "./dashboards/SuperAdminDashboard";
import PharmacyAdminDashboard from "./dashboards/PharmacyAdminDashboard";
import DruggistDashboard from "./dashboards/DruggistDashboard";
import CustomerDashboard from "./dashboards/CustomerDashboard";
import Header from "./Header";

const MainSection = () => {
  const [isSignIn, setIsSignIn] = useState(localStorage.getItem("token")? true : false);
  const role = localStorage.getItem("role");
  useEffect(() => {
      const token = localStorage.getItem("token");
      if (token && role) {
        setIsSignIn(true);
      } else {
        setIsSignIn(false);
      }
    }, []);



  const renderDashboard = () => {
   
    switch (role) {
      case "SUPER_ADMIN":
        return <SuperAdminDashboard />;
      case "PHARMACY_ADMIN":
        return <PharmacyAdminDashboard />;
      case "DRUGGIST":
        return <DruggistDashboard />;
      case "CUSTOMER":
        return <CustomerDashboard />;
      default:
        return <p>Unauthorized access</p>;
    }
  };

  return <div> {isSignIn && <Header />}{renderDashboard()}</div>;
};

export default MainSection;
