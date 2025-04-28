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
@Table(name="orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;
	
	@Column(name = "order_user_id", nullable = false)
    private int user;
	
	@Column( name = "order_medicineId_list")
	private List<Long> medicineList;
	
	@Column(name = "order_total_amount")
	private float totalOrderAmount;
	
	@Column(name = "order_date")
	private LocalDate orderDate;
	
	@Column(name = "order_transaction_id")
	private long transactionId;

	/**
	 * @param id
	 * @param user
	 * @param medicineList
	 * @param totalOrderAmount
	 * @param orderDate
	 * @param transactionId
	 */
	public Order( int user, List<Long> medicineList, float totalOrderAmount, LocalDate orderDate,
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
	public int getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(int user) {
		this.user = user;
	}

	/**
	 * @return the medicineList
	 */
	public List<Long> getMedicineList() {
		return medicineList;
	}

	/**
	 * @param medicineList the medicineList to set
	 */
	public void setMedicineList(List<Long> medicineList) {
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
