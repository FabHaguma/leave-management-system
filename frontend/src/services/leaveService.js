// src/services/leaveService.js
import { apiRequest } from './apiConfig';

// Function to fetch all leave requests for the user
export const fetchLeaveRequests = async () => {
  try {
    return await apiRequest('/leaves', {
      method: 'GET',
    });
  } catch (error) {
    console.error('Failed to fetch leave requests:', error);
    throw error;
  }
};

// Function to fetch leave balance for the user
export const fetchLeaveBalance = async () => {
  try {
    return await apiRequest('/leaves/balance', {
      method: 'GET',
    });
  } catch (error) {
    console.error('Failed to fetch leave balance:', error);
    throw error;
  }
};

// Function to create a new leave request
export const createLeaveRequest = async (requestData) => {
  try {
    return await apiRequest('/leaves', {
      method: 'POST',
      body: JSON.stringify(requestData),
    });
  } catch (error) {
    console.error('Failed to create leave request:', error);
    throw error;
  }
};

// Function to fetch pending requests (for managers/admins)
export const fetchPendingApprovals = async () => {
  try {
    return await apiRequest('/leaves/pending', {
      method: 'GET',
    });
  } catch (error) {
    console.error('Failed to fetch pending approvals:', error);
    throw error;
  }
};

// Function to approve a leave request
export const approveLeaveRequest = async (requestId, remarks = '') => {
  try {
    return await apiRequest(`/leaves/${requestId}/approve`, {
      method: 'PUT',
      body: JSON.stringify({ remarks }),
    });
  } catch (error) {
    console.error('Failed to approve leave request:', error);
    throw error;
  }
};

// Function to reject a leave request
export const rejectLeaveRequest = async (requestId, remarks = '') => {
  try {
    return await apiRequest(`/leaves/${requestId}/reject`, {
      method: 'PUT',
      body: JSON.stringify({ remarks }),
    });
  } catch (error) {
    console.error('Failed to reject leave request:', error);
    throw error;
  }
};

// Function to cancel a leave request
export const cancelLeaveRequest = async (requestId) => {
  try {
    return await apiRequest(`/leaves/${requestId}/cancel`, {
      method: 'PUT',
    });
  } catch (error) {
    console.error('Failed to cancel leave request:', error);
    throw error;
  }
};

// Function to fetch team leave calendar
export const fetchTeamLeaveCalendar = async () => {
  try {
    return await apiRequest('/leaves/team-calendar', {
      method: 'GET',
    });
  } catch (error) {
    console.error('Failed to fetch team leave calendar:', error);
    throw error;
  }
};