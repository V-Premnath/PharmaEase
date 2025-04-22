import React, { useEffect, useState } from 'react';
import axios from 'axios';

const Notifications = ({ username, role }) => {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);

  const endpointMap = {
    SUPER_ADMIN: '/api/notifications/superadmin',
    PHARMACY_ADMIN: '/api/notifications/pharmacyadmin',
    DRUGGIST: '/api/notifications/druggist',
    VENDOR: '/api/notifications/vendor',
    CUSTOMER: '/api/notifications/customer',
  };

  const fetchNotifications = async () => {
    try {
      const res = await axios.post(endpointMap[role], {});
       setNotifications(res.data);
    } catch (err) {
      console.error("Failed to fetch notifications", err);
    } finally {
      setLoading(false);
    }
  };

  const markAsRead = async (id) => {
    try {
      await axios.put(`/api/notification/mark-read/${id}`);
      setNotifications(prev =>
        prev.map(n => n.id === id ? { ...n, readFalse: false } : n)
      );
    } catch (err) {
      console.error("Error marking notification as read", err);
    }
  };

  useEffect(() => {
    fetchNotifications();
  }, [username]);

  if (loading) return <div>Loading notifications...</div>;

  return (
    <div className="p-4 bg-white shadow rounded-md w-full max-w-lg">
      <h2 className="text-xl font-semibold mb-3">Notifications</h2>
      {notifications.length === 0 ? (
        <p>No new notifications</p>
      ) : (
        <ul className="space-y-2">
          {notifications.map(not => (
            <li
              key={not.id}
              className={`p-3 border rounded cursor-pointer transition ${
                not.readFalse ? 'bg-yellow-100' : 'bg-gray-100'
              }`}
              onClick={() => markAsRead(not.id)}
            >
              <p className="text-sm">{not.message}</p>
              <span className="text-xs text-gray-500">{new Date(not.createdAt).toLocaleString()}</span>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Notifications;
