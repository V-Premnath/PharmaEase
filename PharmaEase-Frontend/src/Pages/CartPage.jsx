import { useState, useEffect } from "react";
import "./CartPage.css";
import api from "../services/axiosConfig";
import { useNavigate } from "react-router-dom";

const CartPage = () => {
  const [productList, setProductList] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchCartProducts();
  }, []);

  const handleCheckout = () => {
    navigate('/checkout');
  }

  const fetchMedicineDetails = async (pharmacyId, medicineId) => {
    try {
      const res = await api.get(`/api/pharmacy/pharmacy-id/${pharmacyId}/medicine/${medicineId}`);
      console.log("Fetched medicine details:", res.data);
      return res.data;
    }
    catch (err) {
      console.error("Error fetching medicine details:", err);
      return null;
    }
  }
  const fetchCartProducts = async () => {
    try {
      const res = await api.get('/api/cart/'); // or your cart API
      const cartItems = res.data;

      console.log("Cart Items:", cartItems);

      // Now for each cart item, fetch medicine details
      const detailedProducts = await Promise.all(
        cartItems.map(async (item) => {
          const medicine = await fetchMedicineDetails(item.pharmacyId, item.medicineId);

          if (medicine) {
            return {
              medicineId: medicine.id,
              medicineName: medicine.name,
              medicinePrice: medicine.cost,
              medicineImage: medicine.img,
              quantity: item.quantity, // quantity from the cart item
            };
          } else {
            return null; // or handle error case
          }
        })
      );

      // Remove nulls if any medicine failed to fetch
      const validProducts = detailedProducts.filter(product => product !== null);

      setProductList(validProducts);

    } catch (error) {
      console.error("Error fetching cart products:", error);
    }
  };


  function getTotalPrice() {
    const total = productList.reduce((sum, product) => sum + (product.medicinePrice * product.quantity), 0);
    return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(total);
  }


  return (
    <div id="container" style={{ paddingTop: "90px" }}>

      <h1 id="cart-header">{productList.length > 0 ? "Shopping Cart" : "Your PharmaEase Cart is empty."}&#x1F6D2;</h1>
      <div className="product-cart-section">
        <div className="product-list">


          <div className="divider"></div>

          {productList.map((product) => (
            <CartItem
              key={product.medicineId}
              id={product.medicineId}
              name={product.medicineName}
              quantity={product.quantity}
              price={product.medicinePrice}
              image={product.medicineImage}
            />
          ))}

          <div className="cart-footer">
          <p className="cart-total">
            Subtotal ({productList.length} items): <b>{getTotalPrice()}</b>
          </p>
          <div className="cart-checkout" style={{display:'flex',flexDirection:'column',alignContent:'center'}}>
            <button onClick={handleCheckout} style={{border:"2px solid #002f40 ",borderRadius:"7px",height:"28px",padding:"2px"}}>Checkout</button></div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CartPage;

// CartItem Component
const CartItem = ({ id, name, quantity, price, image }) => {
  return (
    <div className="cart" key={id}>
      <div className="cart-img">
        <img src={image} alt={name} className="cart-product-img" />
      </div>
      <div className="cart-detail" style={{ flexGrow: 1 }}>
        <p className="cart-title">{name}</p>
        <p className="cart-subtext">ID: {id}</p>
        <p className="cart-subtext">Quantity: {quantity}</p>
        <p className="cart-price">₹ {price}</p>
      </div>
    </div>
  );
};
