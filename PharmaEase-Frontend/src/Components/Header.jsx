import React, { useContext } from "react";
import SearchIcon from "@mui/icons-material/Search";
import AddShoppingCartIcon from "@mui/icons-material/AddShoppingCart";
import AuthContext from "../ContextApi/AuthContext";  
import { Link } from "react-router-dom";
import { Notifications } from "@mui/icons-material";
// import "./Header.css";

const Header = () => {

  const { isSignIn } = useContext(AuthContext);



  const handleNotificationClick = () => {
    window.location.href = "/notifications";
  }

  const handleSignin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/google";
  };

  const handleLogout = () => {
    localStorage.clear();
    window.location.href = "/login";
  };

  const handleTemp = async () => {
    window.location.href = "/home";
  }

  return (
    <>
      <div className="header-container" style={{ backgroundColor: '#002F40' }}>
        <div className="header-logoBar flexBox">
          <div className="logo" onClick={() => handleTemp()}>
            <Link to={'/home'}>
              <img className="image" src="../image/Print.svg" alt="" />
            </Link>
          </div>
          <div className="header-addSelector flexBox">
          </div>
        </div>
        {localStorage.getItem("role") == 'CUSTOMER' && (
          <div className="header-searchBar" >
          <div className="headerSearchBar flexBox">
            <input
              className="searchBar"
              type="text"
              placeholder="Search PharmaEase"
            />
            <button className="searchButton" style={{ backgroundColor: '#54eea1' }}>
              <SearchIcon />
            </button>
          </div>
        </div>)}
        <div className="header-menuBar flexBox">
          {isSignIn &&
            <div className="menu-items" onClick={handleLogout} >
              <span>
                Hello, <span style={{ color: '#ffffff' }}>{localStorage.getItem("role")}</span>
              </span>
              <p className="bold">Logout</p>
            </div>// :
            // <div className="menu-items" onClick={handleSignin}>
            //   <span>
            //     Hello, <span>Sign into</span>
            //   </span>
            //   <p className="bold">Account</p>
            // </div>
          }

          {localStorage.getItem("role") == 'CUSTOMER' ? 
            <Link to={"/CartPage"}>
              <div className="menu-items flexBox">
                <div className="bold">
                  <AddShoppingCartIcon />
                </div>
                <div>
                  <p className="bold">&nbsp;Cart</p>
                </div>
              </div>
            </Link> :
            <div onClick={handleNotificationClick} style={{cursor:"pointer"}}><Notifications /></div>}
        </div>
      </div>
    </>
  );
};

export default Header;
