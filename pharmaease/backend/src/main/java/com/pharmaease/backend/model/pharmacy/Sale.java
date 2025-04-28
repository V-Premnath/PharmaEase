
package com.pharmaease.backend.model.pharmacy;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;
    
    @Column(name = "sale_user_id", nullable = true)
    private Long userId;
    
    @Column(name = "sale_druggist_id", nullable = false)
    private Long druggistId;
    
    @Column( name = "sale_medicine_list")
	private List<Long> medicineList;
    
    @Column(name = "sale_total_amount", nullable = false)
    private double totalAmount;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sale_date")
    private Date salesDate;
    
    @Column(name = "sale_payment_method", nullable = false)
    private String paymentMethod;
    
    @Column(name = "sale_transaction_id",nullable =true)
    private String transactionId;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUser() { return userId; }
    public void setUser(Long user) { this.userId = user; }
    public Long getDruggist() { return druggistId; }
    public void setDruggist(Long druggist) { this.druggistId = druggist; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public Date getSalesDate() { return salesDate; }
    public void setSalesDate(Date salesDate) { this.salesDate = salesDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
	@Override
	public String toString() {
		return "Sale [id=" + id + ", user=" + userId + ", druggist=" + druggistId + ", totalAmount=" + totalAmount
				+ ", salesDate=" + salesDate + ", paymentMethod=" + paymentMethod + ", transactionId=" + transactionId
				+ "]";
	}
	/**
	 * @param user
	 * @param druggist
	 * @param totalAmount
	 * @param salesDate
	 * @param paymentMethod
	 * @param transactionId
	 */
	public Sale(Long user, Long druggist, double totalAmount, Date salesDate, String paymentMethod,
			String transactionId) {
		this.userId = user;
		this.druggistId = druggist;
		this.totalAmount = totalAmount;
		this.salesDate = salesDate;
		this.paymentMethod = paymentMethod;
		this.transactionId = transactionId;
	}
	public Sale(){}
	}
