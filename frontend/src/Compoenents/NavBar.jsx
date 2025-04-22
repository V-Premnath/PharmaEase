import { useContext, useState } from "react";
import NavBarItem from "../Details/NavBarItem"
import MenuIcon from '@mui/icons-material/Menu';
import CloseIcon from '@mui/icons-material/Close';
import LeftBar from "./LeftBar";
import CartContext from "../ContextApi/CartContext";

const NavBar = () => {

  const { openButton }  = useContext(CartContext);

  return (
    <>
      <div id="Navbar-container" className="flexBox">
        <div className="leftBarSection" style={{display: 'flex', justifyContent: 'left', alignItems: 'center',marginLeft: '-40rem'}}>
            <div onClick={() => openButton()}><MenuIcon /></div>
        
             <LeftBar/>
        </div>
        <div className="Navbar-menu">
            <ul>
                {/* {
                  NavBarItem.map((item)=>(<li className="Navbar-menu-item" key={item.id}>{item.MenuItem}</li>))
                } */}
            </ul>
        </div>
        
            
       
      </div>
    </>
  )
}

export default NavBar