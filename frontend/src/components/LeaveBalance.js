import React, { useEffect, useState } from 'react';
import Navbar from './Navbar';
import '../styles/LeaveBalance.css';

const LeaveBalance = () => {
  const [leaveData, setLeaveData] = useState({ used: 0, remaining: 0, entitlement: 0 });

  useEffect(() => {
    // Simulate API call to fetch leave balance data
    const fetchLeaveData = async () => {
      try {
        const response = {
          used: 5,
          remaining: 15,
          entitlement: 20,
        };
        setLeaveData(response);
      } catch (error) {
        console.error('Failed to fetch leave balance data:', error);
      }
    };

    fetchLeaveData();
  }, []);

  return (
    <div className="LMS-general-page-container">
      <Navbar />
      <div className="leave-balance-content">
        <h2>Leave Balance</h2>
        <table className="leave-balance-table">
          <thead>
            <tr>
              <th>Used Days</th>
              <th>Remaining Days</th>
              <th>Total Entitlement</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{leaveData.used}</td>
              <td>{leaveData.remaining}</td>
              <td>{leaveData.entitlement}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default LeaveBalance;