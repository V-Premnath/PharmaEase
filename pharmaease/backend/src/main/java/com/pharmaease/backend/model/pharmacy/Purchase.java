package com.pharmaease.backend.model.pharmacy;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "purchases")
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="purchase_id")
	private Long id;
	
	@Column(name="vendor_id", nullable =false)
	private int vendor;

	// One Purchase can have multiple Medicines
	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
	private List<Medicine> medicines;	

	@Column(name= "purcahse_total_amount")
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
	public int getVendor() {
		return vendor;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(int vendor) {
		this.vendor = vendor;
	}

	/**
	 * @return the medicines
	 */
	public List<Medicine> getMedicines() {
		return medicines;
	}

	/**
	 * @param medicines the medicines to set
	 */
	public void setMedicines(List<Medicine> medicines) {
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
		return "Purchase [id=" + id + ", vendor=" + vendor + ", medicines=" + medicines + ", purchaseTotalAmount="
				+ purchaseTotalAmount + ", purchaseDate=" + purchaseDate + "]";
	}

	/**
	 * @param vendor
	 * @param medicines
	 * @param purchaseTotalAmount
	 * @param purchaseDate
	 */
	public Purchase(int vendor, List<Medicine> medicines, float purchaseTotalAmount, LocalDate purchaseDate) {
		this.vendor = vendor;
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