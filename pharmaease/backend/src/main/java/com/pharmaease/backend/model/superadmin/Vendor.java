
package com.pharmaease.backend.model.superadmin;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "vendors")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vendor_id")
    private Long id;
    
    @Column(name="vendor_name",nullable = false, unique = true)
    private String name;
    
    @Column(name= "vendor_contact_info",nullable = false)
    private String contactInfo;
    
    @ElementCollection
    @CollectionTable(name = "vendor_medicines", joinColumns = @JoinColumn(name = "vendor_id"))
    @Column(name = "medicine_id")
    private Set<Long> medicines_ids;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public Set<Long> getMedicines() { return medicines_ids; }
    public void setMedicines(Set<Long> medicines) { this.medicines_ids = medicines; }
	/**
	 * @param id
	 * @param name
	 * @param contactInfo
	 * @param medicines
	 */
	public Vendor( String name, String contactInfo, Set<Long> medicines) {
//		this.id = id; Uto Generated
		this.name = name;
		this.contactInfo = contactInfo;
		this.medicines_ids = medicines;
	}
	public Vendor() {}
	@Override
	public String toString() {
		return "Vendor [id=" + id + ", name=" + name + ", contactInfo=" + contactInfo + ", medicines=" + medicines_ids
				+ "]";
	}

}
