package com.pharmaease.backend.model.pharmacy;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "medicines")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Long id;
    
    @Column(name = "medicine_name",nullable = false)
    private String name;
    
    @Column(name = "medicine_generic_name",nullable = false)
    private String genericName;
    
    @Column(name= "medicine_composition",nullable = false)
    private String composition;
    
    @Column(name = "medicine_stock",nullable = false)
    private int stock;
    
	@Column(name = "medicine_purchase_id", nullable = true)
	private Long purchase;  // Tracks the purchase this medicine belongs to
    
    @Column(name = "medicine_cost",nullable = false)
    private double cost;
    
    
    @Column(name = "medicine_type",nullable = false)
    private String type;
    
    @Column(name = "medicine_mfg_date",nullable = false)
    private LocalDate mfgDate;
    
    @Column(name = "medicine_exp_date",nullable = false)
    private LocalDate expDate;
    
    @Column(name= "medicine_mfg_by",nullable = false)
    private String mfgBy;
    
    @Column(name= "medicine_image_url",nullable = true,length=1024)
    private String img;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGenericName() { return genericName; }
    public void setGenericName(String genericName) { this.genericName = genericName; }
    public String getComposition() { return composition; }
    public void setComposition(String composition) { this.composition = composition; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public Long getVendor() { return purchase; }
    public void setVendor(Long purchase) { this.purchase = purchase; }
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public LocalDate getMfgDate() { return mfgDate; }
    public void setMfgDate(LocalDate mfgDate) { this.mfgDate = mfgDate; }
    public LocalDate getExpDate() { return expDate; }
    public void setExpDate(LocalDate expDate) { this.expDate = expDate; }
    public String getMfgBy() { return mfgBy; }
    public void setMfgBy(String mfgBy) { this.mfgBy = mfgBy; }
    public Long getMedicinePurchasedFrom() { return purchase; }
    public void MedicinePurchasedFrom(Long purchase) { this.purchase = purchase ; }
    public String getImg() { return img; }
    public void setImg(String Img) { this.img = Img; }
	/**
	 * @param id
	 * @param name
	 * @param genericName
	 * @param composition
	 * @param stock
	 * @param purchase
	 * @param cost

	 * @param type
	 * @param mfgDate
	 * @param expDate
	 * @param mfgBy
	 */
	public Medicine( String name, String genericName, String composition, int stock, Long purchase,
			double cost,  String type, LocalDate mfgDate, LocalDate expDate, String mfgBy) {
		// this.id = id; Auto Generated
		this.name = name;
		this.genericName = genericName;
		this.composition = composition;
		this.stock = stock;
		this.purchase = purchase;
		this.cost = cost;
		this.type = type;
		this.mfgDate = mfgDate;
		this.expDate = expDate;
		this.mfgBy = mfgBy;
	}
	public Medicine() {
		
	}
	@Override
	public String toString() {
		return "Medicine [id=" + id + ", name=" + name + ", genericName=" + genericName + ", composition=" + composition
				+ ", stock=" + stock + ", purchase=" + purchase + ", cost=" + cost 
				+ ", type=" + type + ", mfgDate=" + mfgDate + ", expDate=" + expDate + ", mfgBy=" + mfgBy + "]";
	}
}
