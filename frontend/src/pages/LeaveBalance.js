import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import '../styles/LeaveBalance.css';
import { AuthContext } from '../context/AuthContext'; // Import AuthContext
const { sendRequest } = require('../utils/urlBuilder');

const LeaveBalance = () => {
  const { user } = React.useContext(AuthContext);
  const [LeaveBalances, setLeaveBalances] = useState([]);

  useEffect(() => {
      const fetchLeaveBalances = async () => {
        const data = await sendRequest(`leave-balances/me/${user.userId}`, 'GET', {});
        setLeaveBalances(data);
      };
  
      fetchLeaveBalances();
  }, []);

  return (
    <div className="LMS-general-page-container">
      <Navbar />
      <div className="leave-balance-content">
        <h2>Leave Balance</h2>
        <table className="leave-balance-table">
          <thead>
            <tr>
              <th>Leave Type Name</th>
              <th>Used Days</th>
              <th>Total Entitlement</th>
              <th>Remaining Days</th>
            </tr>
          </thead>
          <tbody>
            {LeaveBalances.map((balance, index) => (
              <tr key={index}>
                <td>{balance.leaveTypeName}</td>
                <td>{balance.used}</td>
                <td>{balance.entitlement}</td>
                <td>{balance.remaining}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default LeaveBalance;