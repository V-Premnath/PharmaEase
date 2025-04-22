package com.pharmaease.backend.model.superadmin;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    
    @Column(name="user_username",nullable = false, unique = true)
    private String username;
    
    @Column(name="user_password",nullable = true)
    private String password;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pharmacy_id", nullable = true)
    private Pharmacy pharmacyId;
    @Column(name = "user_email",nullable =false,unique =true)
    private String userEmail;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String useremail) { this.userEmail = useremail; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
	/**
	 * @param username
	 * @param password
	 * @param role
	 * @param pharmacyId
	 */
	public User(String username, String password, Role role, Pharmacy pharmacyId,String email) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.pharmacyId = pharmacyId;
		this.userEmail = email;
	}
	public User() {}
    
    public User(User a) {
    this.username = a.username;
	this.password = a.password;
	this.role = a.role;
	this.pharmacyId = a.pharmacyId;
	this.userEmail = a.userEmail;}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", pharmacyId=" + pharmacyId +"Email="+userEmail+ "]";
	}
	/**
	 * @return the pharmacyId
	 */
	public Pharmacy getPharmacy() {
		return pharmacyId;
	}
	/**
	 * @param pharmacyId the pharmacyId to set
	 */
	public void setPharmacy(Pharmacy pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
}