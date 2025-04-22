package com.pharmaease.backend.repository.superadmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.pharmaease.backend.model.superadmin.Notification;
@Repository
public interface NotificationRepo extends JpaRepository<Notification,Long> {
		List<Notification> findAllByReceiverIdAndIsReadFalse(Long id);

		@Modifying
		@Transactional
		@Query("UPDATE Notification n SET n.isRead = true WHERE n.id = :id")
		void markAsRead(@Param("id") Long id);
}
