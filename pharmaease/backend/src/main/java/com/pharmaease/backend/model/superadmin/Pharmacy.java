package com.pharmaease.backend.model.superadmin;

import jakarta.persistence.*;


@Entity
@Table(name = "pharmacies")
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pharmacy_id")
    private Long id;
    
    @Column(name="pharmacy_name",nullable = false, unique = true)
    private String name;
    
    @Column(name="pharmacy_address",nullable = false)
    private String address;
    
    @Column(name="pharmacy_admin_id",nullable = false, unique = true)
    private String adminUserid;
    
    @Column(name = "pharmacy_access_granted",nullable = false)
    private Boolean accessGranted;
    
    @Column(name ="pharmacy_database_name",nullable = false, unique = true)
    private String databaseName;
    
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getAdminUserid() { return adminUserid; }
    public void setAdminUserid(String adminUsername) { this.adminUserid = adminUsername; }
    public Boolean getAccessGranted() { return accessGranted; }
    public void setAccessGranted(Boolean accessGranted) { this.accessGranted = accessGranted; }
    public String getDatabaseName() {return databaseName;}
    public void setDatabaseName(String dbName) {this.databaseName = dbName;}
	/**
	 * @param name
	 * @param address
	 * @param adminUserid
	 * @param accessGranted
	 * @param databaseName
	 */
	public Pharmacy(String name, String address, String adminUserid, Boolean accessGranted, String databaseName) {
		this.name = name;
		this.address = address;
		this.adminUserid = adminUserid;
		this.accessGranted = accessGranted;
		this.databaseName = databaseName;
	}
	public Pharmacy() {}
	@Override
	public String toString() {
		return "Pharmacy [id=" + id + ", name=" + name + ", address=" + address + ", adminUserid=" + adminUserid
				+ ", accessGranted=" + accessGranted + ", databaseName=" + databaseName + "]";
	}
	
    
}
