package com.pharmaease.backend.model.superadmin;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ElementCollection
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    private List<CartItem> cartItems = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<CartItem> getCartMedicines() { return cartItems; }
	public void setCartMedicines(List<CartItem> medicines) { this.cartItems = medicines; }
	
	/**
	 * @param user
	 * @param cartMedicinesList
	 */
	public Cart(User user, List<CartItem> cartMedicinesList) {
		this.user = user;
		this.cartItems = cartMedicinesList;
	}
	public Cart() {}
	@Override
	public String toString() {
		return "Cart [id=" + id + ", user=" + user + ", cartMedicinesList=" + cartItems + "]";
	}
	
	
}

@Embeddable
class CartItem {
    private Long medicineId;
    private int quantity;

    public CartItem() {}

    public CartItem(Long medicineId, int quantity) {
        this.medicineId = medicineId;
        this.quantity = quantity;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
