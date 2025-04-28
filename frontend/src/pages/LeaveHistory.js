import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar'; // Import Navbar
import '../styles/LeaveHistory.css'; // Add CSS for styling
import { AuthContext } from '../context/AuthContext'; // Import AuthContext
const { sendRequest } = require('../utils/urlBuilder');

const LeaveHistory = () => {
  const { user } = React.useContext(AuthContext); // Get user info from AuthContext
  const [leaveHistory, setLeaveHistory] = useState([]); // State to store leave history
  const [error, setError] = useState(''); // State to store errors

  useEffect(() => {
        const fetchLeaveHistory = async () => {
          const data = await sendRequest(`leave-requests/my-history/${user.id}`, 'GET', {});
          setLeaveHistory(data);
        };
    
        fetchLeaveHistory();
  }, []);

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
                <th>Leave Type</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {leaveHistory.map((leave, index) => (
                <tr key={index}>
                  <td>{leave.leaveTypeName}</td>
                  <td>{leave.startDate}</td>
                  <td>{leave.endDate}</td>
                  <td>{leave.status}</td>
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