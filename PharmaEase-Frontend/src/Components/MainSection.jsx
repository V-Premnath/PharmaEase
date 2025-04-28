
import SuperAdminDashboard from "./dashboards/SuperAdminDashboard";
import PharmacyAdminDashboard from "./dashboards/PharmacyAdminDashboard";
import DruggistDashboard from "./dashboards/DruggistDashboard";
import CustomerDashboard from "./dashboards/CustomerDashboard";
import VendorDashboard from "./dashboards/VendorDashboard";


const MainSection = () => {
  const role = localStorage.getItem("role");
  const username = localStorage.getItem("username");
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
      case "VENDOR":
        return <VendorDashboard />;
      default:
        return <p>Unauthorized access</p>;
    }
  };

  return <div style={{ paddingTop: "50px" }}> {renderDashboard()}</div>;
};

export default MainSection;
