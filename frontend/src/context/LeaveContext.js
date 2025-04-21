import React, { createContext, useState, useContext, useEffect, useCallback } from 'react';
import { useAuth } from './AuthContext';
import * as leaveService from '../services/leaveService';

const LeaveContext = createContext();

export const LeaveProvider = ({ children }) => {
  const { token, user } = useAuth(); // Need auth token for API calls
  const [leaveRequests, setLeaveRequests] = useState([]);
  const [leaveBalance, setLeaveBalance] = useState(null); // { type: balance }
  const [pendingApprovals, setPendingApprovals] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] =useState(null);

  const fetchLeaves = useCallback(async () => {
    if (!token || !user) return; // Don't fetch if not logged in
    setLoading(true);
    setError(null);
    try {
      // API calls
      const [requests, balance] = await Promise.all([
        leaveService.fetchLeaveRequests(),
        leaveService.fetchLeaveBalance(),
      ]);
      
      setLeaveRequests(requests);
      setLeaveBalance(balance);

      // If user is a manager or admin, also fetch pending approvals
      if (user.role === 'Manager' || user.role === 'Admin') {
        const approvals = await leaveService.fetchPendingApprovals();
        setPendingApprovals(approvals);
      }
    } catch (err) {
      console.error("Failed to fetch leave data:", err);
      setError("Could not load leave information.");
    } finally {
      setLoading(false);
    }
  }, [token, user]); // Depend on token and user

  // Fetch data when user logs in
  useEffect(() => {
    fetchLeaves();
  }, [fetchLeaves]); // Use the memoized fetchLeaves

  // Function to add a new leave request
  const applyForLeave = async (requestData) => {
    if (!token) return false;
    setLoading(true);
    setError(null);
    try {
      const newRequest = await leaveService.createLeaveRequest(requestData);
      setLeaveRequests(prev => [...prev, newRequest]);
      // Refresh leave data after application
      await fetchLeaves(); 
      return true;
    } catch (err) {
      console.error("Failed to apply for leave:", err);
      setError("Failed to submit leave application.");
      setLoading(false); // Ensure loading is reset on error
      return false;
    }
  };

  // Function for managers to approve a leave request
  const approveRequest = async (requestId, remarks = '') => {
    if (!token) return false;
    setLoading(true);
    setError(null);
    try {
      await leaveService.approveLeaveRequest(requestId, remarks);
      // Refresh data after approval
      await fetchLeaves();
      return true;
    } catch (err) {
      console.error("Failed to approve leave:", err);
      setError("Failed to approve leave request.");
      setLoading(false);
      return false;
    }
  };

  // Function for managers to reject a leave request
  const rejectRequest = async (requestId, remarks = '') => {
    if (!token) return false;
    setLoading(true);
    setError(null);
    try {
      await leaveService.rejectLeaveRequest(requestId, remarks);
      // Refresh data after rejection
      await fetchLeaves();
      return true;
    } catch (err) {
      console.error("Failed to reject leave:", err);
      setError("Failed to reject leave request.");
      setLoading(false);
      return false;
    }
  };

  // Function for users to cancel their own leave request
  const cancelRequest = async (requestId) => {
    if (!token) return false;
    setLoading(true);
    setError(null);
    try {
      await leaveService.cancelLeaveRequest(requestId);
      // Refresh data after cancellation
      await fetchLeaves();
      return true;
    } catch (err) {
      console.error("Failed to cancel leave:", err);
      setError("Failed to cancel leave request.");
      setLoading(false);
      return false;
    }
  };

  return (
    <LeaveContext.Provider 
      value={{ 
        leaveRequests, 
        leaveBalance, 
        pendingApprovals,
        loading, 
        error, 
        applyForLeave, 
        approveRequest,
        rejectRequest,
        cancelRequest,
        refetchLeaves: fetchLeaves 
      }}
    >
      {children}
    </LeaveContext.Provider>
  );
};

export const useLeave = () => useContext(LeaveContext);