package com.pharmaease.backend.model.superadmin;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

	@Column(name ="notification_sender_id")
	private Long senderId;
	
	@Column(name ="notification_receiver_id", nullable= true)
	private Long receiverId;
	
	@Column(name ="notification_message")
	private String message;
	
	@Column(name= "notification_time")
	private LocalDateTime time;
	
	@Column(name = "is_read",nullable=false)
	private boolean isRead = false;


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
	 * @return the senderId
	 */
	public Long getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return the receiverId
	 */
	public Long getReceiverId() {
		return receiverId;
	}

	/**
	 * @param receiverId the receiverId to set
	 */
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the time
	 */
	public LocalDateTime getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	/**
	 * @param senderId
	 * @param receiverId
	 * @param message
	 * @param time
	 */
	public Notification(Long senderId, Long receiverId, String message, LocalDateTime time) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.message = message;
		this.time = time;
	}
	public Notification() {}

	/**
	 * @return the isRead
	 */
	public boolean getIsRead() {
		return isRead;
	}

	/**
	 * @param isRead the isRead to set
	 */
	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", senderId=" + senderId + ", receiverId=" + receiverId + ", message="
				+ message + ", time=" + time + ", isRead=" + isRead + "]";
	}
	
}
