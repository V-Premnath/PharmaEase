import React, { useEffect, useState } from 'react';
import api from '../services/axiosConfig';
import './Notifications.css';

const Notifications = () => {
  const role = localStorage.getItem('role');
  const username = localStorage.getItem('username');
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);

  const endpoint = "api/notifications/"

  const fetchNotifications = async () => {
    try {
      const res = await api.post(endpoint, {});
      setNotifications(res.data);
      console.log("Fetched notifications:", res.data);
    } catch (err) {
      console.error("Failed to fetch notifications", err);
    } finally {
      setLoading(false);
    }
  };

  const handleApprove = async (id,sendId) => {
    try {
      if(role === "SUPER_ADMIN"){
      await api.get(`/api/notifications/superadmin/approve-pharmacy?id=${id}&senderId=${sendId}`);}
      else if(role === "PHARMACY_ADMIN"){
        await api.get(`/api/notifications/pharmacyadmin/approve-druggist?id=${id}&senderId=${sendId}`);
      }
      setNotifications(prev => prev.filter(n => n.id !== id));
    } catch (err) {
      console.error("Error approving notification", err);
    }
  };

  const handleDisapprove = async (id,sendId) => {
    try {
      if (role==="SUPER_ADMIN"){
      await api.get(`/api/notifications/superadmin/disapprove-pharmacy?id=${id}&senderId=${sendId}`);}
      else if (role==="PHARMACY_ADMIN"){
        await api.get(`/api/notifications/pharmacyadmin/disapprove-druggist?id=${id}&senderId=${sendId}`);
      }
      console.log("Disapproved notification:", id, );
      setNotifications(prev => prev.filter(n => n.id !== id));
    } catch (err) {
      console.error("Error disapproving notification", err);
    }
  };

  useEffect(() => {
    fetchNotifications();
  }, [username]);

  if (loading) {
    return (
      <div className="notifications-loading">
        <div className="loading-spinner"></div>
        <p>Loading notifications...</p>
      </div>
    );
  }

  return (
    <div className="notifications-page" style={{ paddingTop: '50px' }}>
      <div className="notifications-header">
        <h1>Notifications</h1>
        <p>Stay updated with your latest alerts and messages</p>
      </div>

      <div className="notifications-content">
        {notifications.length === 0 ? (
          <div className="empty-notifications">
            <div className="empty-icon">
              <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round">
                <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path>
                <path d="M13.73 21a2 2 0 0 1-3.46 0"></path>
              </svg>
            </div>
            <h3>No new notifications</h3>
            <p>When you receive notifications about orders, inventory alerts, or system messages, they will appear here.</p>
          </div>
        ) : (
          <ul className="notifications-list">
            {notifications.map(not => (
              <li key={not.id} className="notification-item unread">
                <div className="notification-icon">
                  {role === 'SUPER_ADMIN' ? (
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#0066cc" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                      <path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                      <circle cx="8.5" cy="7" r="4"></circle>
                      <line x1="20" y1="8" x2="20" y2="14"></line>
                      <line x1="23" y1="11" x2="17" y2="11"></line>
                    </svg>
                  ) : (
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#0066cc" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                      <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path>
                      <path d="M13.73 21a2 2 0 0 1-3.46 0"></path>
                    </svg>
                  )}
                </div>
                <div className="notification-content">
                  <p className="notification-message">{not.message}</p>
                  <p className="notification-message">{not.senderId}</p>
                  <span className="notification-time">{new Date(not.time).toLocaleString()}</span>
                </div>
                {(
                  <div className="notification-actions">
                    <button className="approve-btn" onClick={() => handleApprove(not.id,not.senderId)}>
                      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                        <path d="M20 6L9 17l-5-5"></path>
                      </svg>
                      Approve
                    </button>
                    <button className="disapprove-btn" onClick={() => handleDisapprove(not.id,not.senderId)}>
                      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                        <line x1="18" y1="6" x2="6" y2="18"></line>
                        <line x1="6" y1="6" x2="18" y2="18"></line>
                      </svg>
                      Disapprove
                    </button>
                  </div>
                )}
              </li>
            ))}
          </ul>
        )}
      </div>

      <div className="notifications-footer">
        <div className="footer-content">
          <p>© {new Date().getFullYear()} PharmaEase. All rights reserved.</p>
        </div>
      </div>
    </div>
  );
};

export default Notifications;
