package com.pharmaease.backend.model.superadmin;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who submits the prescription (customer)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // The druggist who approves the prescription
    @ManyToOne
    @JoinColumn(name = "approved_by_druggist_id")
    private User approvedByDruggist;

    @Column(name = "prescription_date", nullable = false)
    private LocalDate prescriptionDate;

    @Column(name = "prescription_approval_status")
    private String prescriptionApprovalStatus; // PENDING, APPROVED, REJECTED

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
	 * @return the approvedByDruggist
	 */
	public User getApprovedByDruggist() {
		return approvedByDruggist;
	}

	/**
	 * @param approvedByDruggist the approvedByDruggist to set
	 */
	public void setApprovedByDruggist(User approvedByDruggist) {
		this.approvedByDruggist = approvedByDruggist;
	}

	/**
	 * @return the prescriptionDate
	 */
	public LocalDate getPrescriptionDate() {
		return prescriptionDate;
	}

	/**
	 * @param prescriptionDate the prescriptionDate to set
	 */
	public void setPrescriptionDate(LocalDate prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}

	/**
	 * @return the prescriptionApprovalStatus
	 */
	public String getPrescriptionApprovalStatus() {
		return prescriptionApprovalStatus;
	}

	/**
	 * @param prescriptionApprovalStatus the prescriptionApprovalStatus to set
	 */
	public void setPrescriptionApprovalStatus(String prescriptionApprovalStatus) {
		this.prescriptionApprovalStatus = prescriptionApprovalStatus;
	}

	@Override
	public String toString() {
		return "Prescription [id=" + id + ", user=" + user + ", approvedByDruggist=" + approvedByDruggist
				+ ", prescriptionDate=" + prescriptionDate + ", prescriptionApprovalStatus="
				+ prescriptionApprovalStatus + "]";
	}

	/**
	 * @param user
	 * @param approvedByDruggist
	 * @param prescriptionDate
	 * @param prescriptionApprovalStatus
	 */
	public Prescription(User user, User approvedByDruggist, LocalDate prescriptionDate,
			String prescriptionApprovalStatus) {
		this.user = user;
		this.approvedByDruggist = approvedByDruggist;
		this.prescriptionDate = prescriptionDate;
		this.prescriptionApprovalStatus = prescriptionApprovalStatus;
	}
	public Prescription() {}
    // Other fields, constructors, getters, and setters...
}

