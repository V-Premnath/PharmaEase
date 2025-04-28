package com.pharmaease.backend.model.superadmin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id",nullable = false)
    private Long id;
    
    @Column(name = "permission_name",nullable = false, unique = true)
    private String name;
    
    @Column(name = "permission_description",length = 255)
    private String description;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	/**
	 * @param name
	 * @param description
	 */
	public Permission(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public Permission(){}
}
