package com.pharmaease.backend.controller.pharmacy;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmaease.backend.service.pharmacy.MedicineService;
import com.pharmaease.backend.service.pharmacy.PharmacyService;
import com.pharmaease.backend.service.superadmin.CartService;
import com.pharmaease.backend.service.superadmin.UserService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("api/cart")
public class CartController {
	private final Logger log = LoggerFactory.getLogger(CartController.class);
	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;
	@Autowired
	private PharmacyService pharmacyService;
	@Autowired 
	MedicineService medicineService;
	
	@GetMapping("/")
	public ResponseEntity<?> getCartMedicines(HttpServletRequest request){
		
		String username = request.getHeader("username");
		
		Long userId= userService.getUserByUsername(username).getId();
		Long cartId = cartService.getCartIdByUserId(userId);
		List<?> cartMedicines = cartService.getCartMedicinesByCartId(cartId);
		log.info("retrieved medicines : "+cartMedicines.get(0)+"  "+cartMedicines.get(1).getClass());
		
		
		
		return ResponseEntity.ok(cartMedicines);
	}
	
	@PostMapping("/add-to-cart")
	public ResponseEntity<?> addToCart(@RequestBody Map<String,String> requestBody,HttpServletRequest request){
		log.info("Username : "+request.getHeader("username"));
		Long userId = userService.getUserByUsername(request.getHeader("username")).getId();
		log.info("UserId : " + userId );
		Long medicineId = Long.parseLong(requestBody.get("medicineId"));
		String pharmDBName = requestBody.get("pharmacyDBName");
		Long pharmId = pharmacyService.getPharmacyByDBName(pharmDBName).getId();

		cartService.addToCart(userId, medicineId, 1, pharmId);
		
//		,Long PharmacyId
		return ResponseEntity.ok(null);
	}

}
