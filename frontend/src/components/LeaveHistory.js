import React, { useEffect, useState } from 'react';
import Navbar from './Navbar'; // Import Navbar
import '../styles/LeaveHistory.css'; // Add CSS for styling
import { AuthContext } from '../context/AuthContext'; // Import AuthContext

const LeaveHistory = () => {
  const { user } = React.useContext(AuthContext); // Get user info from AuthContext
  const [leaveHistory, setLeaveHistory] = useState([]); // State to store leave history
  const [error, setError] = useState(''); // State to store errors

  useEffect(() => {
    const fetchLeaveHistory = async () => {
      try {
        // Simulate API call to fetch leave history (replace with actual API call)
        const response = [
          { startDate: '2025-01-01', endDate: '2025-01-05', status: 'Approved', reason: 'Vacation' },
          { startDate: '2025-02-10', endDate: '2025-02-12', status: 'Rejected', reason: 'Personal reasons' },
          { startDate: '2025-03-15', endDate: '2025-03-20', status: 'Pending', reason: 'Medical leave' },
        ];
        setLeaveHistory(response); // Set the fetched data
      } catch (err) {
        setError('Failed to fetch leave history. Please try again later.');
      }
    };

    fetchLeaveHistory();
  }, [user.id]); // Fetch leave history when component mounts

  return (
    <div className="LMS-general-page-container">
      <Navbar /> {/* Navbar placed above the content */}
      <div className="history-container">
        <h2>Leave History</h2>
        {error && <p className="error-message">{error}</p>}
        {leaveHistory.length === 0 ? (
          <p>No leave history available.</p>
        ) : (
          <table className="leave-history-table">
            <thead>
              <tr>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Status</th>
                <th>Reason</th>
              </tr>
            </thead>
            <tbody>
              {leaveHistory.map((leave, index) => (
                <tr key={index}>
                  <td>{leave.startDate}</td>
                  <td>{leave.endDate}</td>
                  <td>{leave.status}</td>
                  <td>{leave.reason}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

export default LeaveHistory;