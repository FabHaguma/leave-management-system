import React, { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import '../styles/NotificationCenter.css';
const { sendRequest } = require('../utils/urlBuilder');

const NotificationCenter = () => {
  const [notifications, setNotifications] = useState([]);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    // Fetch notifications and user role
    const fetchNotifications = async () => {
      
      try {
        const response = await fetch('/api/notifications');
        const data = await response.json();
        setNotifications(data.notifications);
        setIsAdmin(data.isAdmin);
      } catch (error) {
        console.error('Error fetching notifications:', error);
      }
    };

    fetchNotifications();
  }, []);

  const handleGenerateReport = () => {
    // Logic to generate and download report
    console.log('Generate report');
  };

  return (
    <div className="LMS-general-page-container">
      <Navbar />
      <div className="content notification-center">
        <h1>Notification Center</h1>
        <button onClick={handleGenerateReport} className="generate-report-button">
          Generate Report
        </button>
        <table className="notification-table">
          <thead>
            <tr>
              <th>Message</th>
              <th>Status</th>
              {isAdmin && <th>Actions</th>}
            </tr>
          </thead>
          <tbody>
            {notifications.map((notification) => (
              <tr key={notification.id}>
                <td>{notification.message}</td>
                <td>{notification.read ? 'Read' : 'Unread'}</td>
                {isAdmin && (
                  <td>
                    <button>Add Comment</button>
                    <button>Set Status</button>
                    <button>Save</button>
                  </td>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default NotificationCenter;