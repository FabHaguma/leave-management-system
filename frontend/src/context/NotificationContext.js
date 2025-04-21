import React, { createContext, useState, useContext, useEffect, useCallback } from 'react';
import { useAuth } from './AuthContext';
import * as notificationService from '../services/notificationService';

const NotificationContext = createContext();

export const NotificationProvider = ({ children }) => {
  const { token, user } = useAuth();
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchUserNotifications = useCallback(async () => {
    if (!token || !user) return;
    setLoading(true);
    setError(null);
    try {
      const fetchedNotifications = await notificationService.fetchNotifications();
      setNotifications(fetchedNotifications);
    } catch (err) {
      console.error("Failed to fetch notifications:", err);
      setError("Could not load notifications.");
    } finally {
      setLoading(false);
    }
  }, [token, user]);

  useEffect(() => {
    fetchUserNotifications();
  }, [fetchUserNotifications]);

  const markAsRead = async (notificationId) => {
    if (!token) return;
    try {
      await notificationService.markNotificationRead(notificationId);
      // Update state locally to avoid refetching
      setNotifications(prev =>
        prev.map(n => (n.id === notificationId ? { ...n, read: true } : n))
      );
    } catch (err) {
      console.error("Failed to mark notification as read:", err);
      setError("Could not update notification.");
    }
  };

  const markAllAsRead = async () => {
    if (!token) return;
    try {
      await notificationService.markAllNotificationsRead();
      // Update all notifications locally
      setNotifications(prev =>
        prev.map(n => ({ ...n, read: true }))
      );
    } catch (err) {
      console.error("Failed to mark all notifications as read:", err);
      setError("Could not update notifications.");
    }
  };

  const unreadCount = notifications.filter(n => !n.read).length;

  return (
    <NotificationContext.Provider value={{ 
      notifications, 
      loading, 
      error, 
      unreadCount, 
      markAsRead, 
      markAllAsRead,
      refetchNotifications: fetchUserNotifications 
    }}>
      {children}
    </NotificationContext.Provider>
  );
};

export const useNotifications = () => useContext(NotificationContext);