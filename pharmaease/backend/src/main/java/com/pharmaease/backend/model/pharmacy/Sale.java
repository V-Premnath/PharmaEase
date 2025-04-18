
package com.pharmaease.backend.model.pharmacy;

import jakarta.persistence.*;

import java.util.Date;

import com.pharmaease.backend.model.superadmin.User;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "druggist_id", nullable = false)
    private User druggist;
    
    @Column(name = "sale_total_amount", nullable = false)
    private double totalAmount;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sales_date")
    private Date salesDate;
    
    @Column(name = "sale_payment_method", nullable = false)
    private String paymentMethod;
    
    @Column(name = "sale_transaction_id")
    private String transactionId;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public User getDruggist() { return druggist; }
    public void setDruggist(User druggist) { this.druggist = druggist; }
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
		return "Sale [id=" + id + ", user=" + user + ", druggist=" + druggist + ", totalAmount=" + totalAmount
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
	public Sale(User user, User druggist, double totalAmount, Date salesDate, String paymentMethod,
			String transactionId) {
		this.user = user;
		this.druggist = druggist;
		this.totalAmount = totalAmount;
		this.salesDate = salesDate;
		this.paymentMethod = paymentMethod;
		this.transactionId = transactionId;
	}
	public Sale(){}
	}
