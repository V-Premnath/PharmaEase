package com.pharmaease.backend.model.pharmacy;

import java.time.LocalDate;
import java.util.List;

import com.pharmaease.backend.model.superadmin.User;

import jakarta.persistence.*;

@Entity
@Table(name="orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@Column( name = "medicine_list")
	private List<Medicine> medicineList;
	
	@Column(name = "total_order_amount")
	private float totalOrderAmount;
	
	@Column(name = "order_date")
	private LocalDate orderDate;
	
	@Column(name = "transaction_id")
	private long transactionId;

	/**
	 * @param id
	 * @param user
	 * @param medicineList
	 * @param totalOrderAmount
	 * @param orderDate
	 * @param transactionId
	 */
	public Order( User user, List<Medicine> medicineList, float totalOrderAmount, LocalDate orderDate,
			long transactionId) {
//		this.id = id; auto generated
		this.user = user;
		this.medicineList = medicineList;
		this.totalOrderAmount = totalOrderAmount;
		this.orderDate = orderDate;
		this.transactionId = transactionId;
	}
	public Order() {}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user + ", medicineList=" + medicineList + ", totalOrderAmount="
				+ totalOrderAmount + ", orderDate=" + orderDate + ", transactionId=" + transactionId + "]";
	}
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the medicineList
	 */
	public List<Medicine> getMedicineList() {
		return medicineList;
	}

	/**
	 * @param medicineList the medicineList to set
	 */
	public void setMedicineList(List<Medicine> medicineList) {
		this.medicineList = medicineList;
	}

	/**
	 * @return the totalOrderAmount
	 */
	public float getTotalOrderAmount() {
		return totalOrderAmount;
	}

	/**
	 * @param totalOrderAmount the totalOrderAmount to set
	 */
	public void setTotalOrderAmount(float totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}

	/**
	 * @return the orderDate
	 */
	public LocalDate getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the transactionId
	 */
	public long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	
	
	
}
