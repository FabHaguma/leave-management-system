import React from 'react';
import { useLeave } from '../context/LeaveContext'; // Updated from contexts to context
import { useAuth } from '../context/AuthContext'; // Updated from contexts to context
import { Link, Navigate } from 'react-router-dom'; // Added Navigate for conditional redirect

const ApprovalPage = () => {
  const { user } = useAuth();
  // TODO: Fetch requests specifically needing *this manager's* approval
  // For now, filter all 'Pending' requests from the general context
  const { leaveRequests, loading, error } = useLeave();

  // Mock approval/rejection functions - implement in LeaveContext later
  const handleApprove = (requestId) => {
    console.log(`Approving request ${requestId}`);
    alert(`Mock: Approved request ${requestId}`);
    // Call context function: approveRequest(requestId);
  };

  const handleReject = (requestId) => {
    const reason = prompt("Reason for rejection (optional):");
    console.log(`Rejecting request ${requestId} with reason: ${reason}`);
    alert(`Mock: Rejected request ${requestId}`);
    // Call context function: rejectRequest(requestId, reason);
  };

  // Ensure user has the correct role (though routing should protect this)
  if (!user || (user.role !== 'Manager' && user.role !== 'Admin')) {
    return <div>Unauthorized Access</div>; // Or redirect
  }

  const pendingRequests = leaveRequests.filter(req => req.status === 'Pending');

  return (
    <div>
      <h2>Leave Requests for Approval</h2>
       <Link to="/">Back to Dashboard</Link>
      <hr/>
      {loading && <p>Loading requests...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {!loading && !error && (
        <>
          {pendingRequests.length === 0 ? (
            <p>No pending leave requests.</p>
          ) : (
            <table border="1" style={{ width: '100%', borderCollapse: 'collapse' }}>
              <thead>
                <tr>
                  <th>Employee</th> {/* TODO: Add employee name */}
                  <th>Start Date</th>
                  <th>End Date</th>
                  <th>Type</th>
                  <th>Reason</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {pendingRequests.map(req => (
                  <tr key={req.id}>
                    <td>{req.employeeName || 'Employee Name'}</td> {/* Mock name */}
                    <td>{req.startDate}</td>
                    <td>{req.endDate}</td>
                    <td>{req.type}</td>
                    <td>{req.reason || '-'}</td>
                    <td>
                      <button onClick={() => handleApprove(req.id)} style={{marginRight: '5px'}}>Approve</button>
                      <button onClick={() => handleReject(req.id)}>Reject</button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </>
      )}
    </div>
  );
};

export default ApprovalPage;