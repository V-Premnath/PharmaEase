package com.pharmaease.backend.service.superadmin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmaease.backend.model.superadmin.Cart;
import com.pharmaease.backend.repository.superadmin.CartRepo;

@Service
public class CartService {

	private final Logger log = LoggerFactory.getLogger(CartService.class);
	
	@Autowired
	private CartRepo cartRepo;
	
	public List<?> getCartMedicinesByCartId(Long cartId){
		
		return cartRepo.findById(cartId).orElse(new Cart()).getCartMedicines();
			
	}
	public void addToCart(Long userId, Long medicineId, int quantity, Long pharmacyId) {
	    Cart cart = cartRepo.findByUserId(userId);
	    log.info("repo cart : "+cart);
	    if(cart==null) {
	    	cart = new Cart(userId);
	    	 log.info("created cart : "+cart);	
	    }

	    cart.addMedicineToCart(medicineId, quantity, pharmacyId);
	    log.info("created cart after adding medicine : "+cart);	
	    cartRepo.saveAndFlush(cart); // Save updated cart
	}
	public Long getCartIdByUserId(Long userId) {
		Cart cart = cartRepo.findByUserId(userId);
		log.info("Found cart : "+cart.toString());
		return cart.getId();
	}

}
