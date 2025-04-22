package com.pharmaease.backend.service.superadmin;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pharmaease.backend.model.superadmin.Notification;
import com.pharmaease.backend.repository.superadmin.NotificationRepo;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepo not;

	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
	
	public List<Notification> getNotificationsByReceiverId(Long id) {
		List<Notification> notifyList = new ArrayList<Notification>(not.findAllByReceiverIdAndIsReadFalse(id));
		if (notifyList.isEmpty() ) {
			return null;
		}
		return notifyList;
	}
	public Notification getNotificationBySenderId(Long id) {
		Notification notif  = not.findById(id).orElse(new Notification());
		logger.info(" in notserviceNOtificatiopn : "+notif.getId());
		return notif;
		
		
	}
	@Transactional
	public void markAsRead(Notification notif) {
		not.markAsRead(notif.getId());
		return;
	}

}
