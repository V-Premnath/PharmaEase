package com.pharmaease.backend.model.pharmacy;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "purchase")
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="purchase_id")
	private Long id;
	
	@Column(name="purchase_vendor_id", nullable =false)
	private Long vendorId;

	// One Purchase can have multiple Medicines
	@Column(name="purchase_medicineId_list")
	private List<Long> medicines;	

	@Column(name= "purchase_total_amount")
	private float purchaseTotalAmount;	
	
	@Column(name = "purchase_date")
	private LocalDate purchaseDate;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the vendor
	 */
	public Long getVendor() {
		return vendorId;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(Long vendor) {
		this.vendorId = vendor;
	}

	/**
	 * @return the medicines
	 */
	public List<Long> getMedicines() {
		return medicines;
	}

	/**
	 * @param medicines the medicines to set
	 */
	public void setMedicines(List<Long> medicines) {
		this.medicines = medicines;
	}

	/**
	 * @return the purchaseTotalAmount
	 */
	public float getPurchaseTotalAmount() {
		return purchaseTotalAmount;
	}

	/**
	 * @param purchaseTotalAmount the purchaseTotalAmount to set
	 */
	public void setPurchaseTotalAmount(float purchaseTotalAmount) {
		this.purchaseTotalAmount = purchaseTotalAmount;
	}

	/**
	 * @return the purchaseDate
	 */
	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", vendor=" + vendorId + ", medicines=" + medicines + ", purchaseTotalAmount="
				+ purchaseTotalAmount + ", purchaseDate=" + purchaseDate + "]";
	}

	/**
	 * @param vendor
	 * @param medicines
	 * @param purchaseTotalAmount
	 * @param purchaseDate
	 */
	public Purchase(Long vendor, List<Long> medicines, float purchaseTotalAmount, LocalDate purchaseDate) {
		this.vendorId = vendor;
		this.medicines = medicines;
		this.purchaseTotalAmount = purchaseTotalAmount;
		this.purchaseDate = purchaseDate;
	}

	/**
	 * NO Argument Constructor
	 */
	public Purchase() {
	}
	

	
	
	
}