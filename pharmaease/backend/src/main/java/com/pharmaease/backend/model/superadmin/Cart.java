package com.pharmaease.backend.model.superadmin;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;
    
   
    @Column(name = "cart_user_id", nullable = false,unique = true)
    private Long userId;
    
    @ElementCollection
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    private List<CartItem> cartItems = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUser() { return userId; }
    public void setUser(Long user) { this.userId = user; }
    public List<CartItem> getCartMedicines() { return cartItems; }
	public void setCartMedicines(List<CartItem> medicines) { this.cartItems = medicines; }
	
	/**
	 * @param user
	 * @param cartMedicinesList
	 */
	public Cart(Long user, List<CartItem> cartMedicinesList) {
		this.userId = user;
		this.cartItems = cartMedicinesList;
	}
	public Cart(Long user) {
		this.userId = user;
		this.cartItems = new ArrayList<CartItem>();
	}
	public Cart() {}
	@Override
	public String toString() {
		return "Cart [id=" + id + ", user=" + userId + ", cartMedicinesList=" + cartItems + "]";
	}
	
	public void addMedicineToCart(Long medicineId, int quantity, Long pharmacyId) {
	    // Check if medicine already exists in cart
	    for (CartItem item : cartItems) {
	        if (item.getMedicineId().equals(medicineId) && item.getPharmacyId().equals(pharmacyId)) {
	            // If already present, update quantity
	            item.setQuantity(item.getQuantity() + quantity);
	            return;
	        }
	    }
	    // If not present, add new CartItem
	    cartItems.add(new CartItem(medicineId, quantity, pharmacyId));
	}

}

@Embeddable
class CartItem {
    private Long cartItemMedicineId;
    private int cartItemQuantity;
    private Long cartItemPharmacyId;
    
    public Long getPharmacyId() {
		return cartItemPharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.cartItemPharmacyId = pharmacyId;
	}

	public CartItem() {}

    public CartItem(Long medicineId, int quantity,Long pid) {
        this.cartItemMedicineId = medicineId;
        this.cartItemQuantity = quantity;
        this.cartItemPharmacyId = pid;
    }

    public Long getMedicineId() {
        return cartItemMedicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.cartItemMedicineId = medicineId;
    }

    public int getQuantity() {
        return cartItemQuantity;
    }

    public void setQuantity(int quantity) {
        this.cartItemQuantity = quantity;
    }

	@Override
	public String toString() {
		return "CartItem [cartItemMedicineId=" + cartItemMedicineId + ", cartItemQuantity=" + cartItemQuantity
				+ ", cartItemPharmacyId=" + cartItemPharmacyId + "]";
	}
}
