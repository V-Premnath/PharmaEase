import React, { useContext } from "react";
import NavBar from "./NavBar";
import SearchIcon from "@mui/icons-material/Search";
import AddShoppingCartIcon from "@mui/icons-material/AddShoppingCart";
import CartContext from "../ContextApi/CartContext";
import api from "../services/axiosConfig";
import { Link } from "react-router-dom";
import { Notifications } from "@mui/icons-material";
const Header = () => {

  const { productList } = useContext(CartContext);
  const token = localStorage.getItem("token");
  const [isSignIn, setIsSignIn] = React.useState(token ? true : false);

  React.useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setIsSignIn(true);
    } else {
      setIsSignIn(false);
    }
  }, []);

  const handleNotificationClick = () => {
    window.location.href = "/notifications";

  }
  const handleSignin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/google";
    // Or whatever your backend OAuth2 login URL is
  };

  const handleLogout = () => {
    localStorage.clear();
    setIsSignIn(false);
    window.location.href = "/login";
  };

  const handleTemp = async () => {
    try {
      const response = await api.get("/api/pharmacy-connection/test");
      console.log("Data:", response.data);
    } catch (err) {
      console.error("Unauthorized or error:", err);
    }
  }


  return (
    <>
      <div className="header-container" style={{ backgroundColor: '#002F40' }}>
        <div className="header-logoBar flexBox">
          <div className="logo" onClick={() => handleTemp()}>
            <Link to={'/home'}>
              <img className="image" src="./image/Print.svg" alt="" />
            </Link>
          </div>
          <div className="header-addSelector flexBox">

          </div>
        </div>
        <div className="header-searchBar">
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
        </div>
        <div className="header-menuBar flexBox">

          {isSignIn ?
            <div className="menu-items" onClick={handleLogout}>
              <span>
                Hello, <span style={{ color: '#ffffff' }}>{localStorage.getItem("role")}</span>
              </span>
              <p className="bold">Logout</p>
            </div> :
            <div className="menu-items" onClick={handleSignin}>
              <span>
                Hello, <span>Sign into</span>
              </span>
              <p className="bold">Account</p>
            </div>
          }

          {localStorage.getItem("role")=='CUSTOMER'?<Link to={"/CartPage"}>
            <div className="menu-items flexBox">
              <div className="bold">
                <AddShoppingCartIcon />
                <span>{productList.length}</span>
              </div>
              <div>
                <p className="bold" >&nbsp;Cart</p>
              </div>
            </div>
          </Link>:<div onClick={handleNotificationClick}><Notifications /></div>}
        </div>
      </div>
    </>
  );
};

export default Header;
