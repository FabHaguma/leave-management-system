// src/services/notificationService.js
import { apiRequest } from './apiConfig';

// Function to fetch notifications for the user
export const fetchNotifications = async () => {
  try {
    return await apiRequest('/notifications', {
      method: 'GET',
    });
  } catch (error) {
    console.error('Failed to fetch notifications:', error);
    throw error;
  }
};

// Function to mark a notification as read
export const markNotificationRead = async (notificationId) => {
  try {
    return await apiRequest(`/notifications/${notificationId}/read`, {
      method: 'PUT',
    });
  } catch (error) {
    console.error('Failed to mark notification as read:', error);
    throw error;
  }
};

// Function to mark all notifications as read
export const markAllNotificationsRead = async () => {
  try {
    return await apiRequest('/notifications/read-all', {
      method: 'PUT',
    });
  } catch (error) {
    console.error('Failed to mark all notifications as read:', error);
    throw error;
  }
};