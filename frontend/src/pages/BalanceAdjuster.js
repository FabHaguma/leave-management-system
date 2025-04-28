import React, { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import '../styles/BalanceAdjuster.css';
import { AuthContext } from '../context/AuthContext'; 
const { sendRequest } = require('../utils/urlBuilder');

const BalanceAdjuster = () => {
  const { user } = React.useContext(AuthContext);

  const [userId, setUserId] = useState('');
  const [days, setDays] = useState(0);
  const [message, setMessage] = useState('');
  const [leaveBalances, setLeaveBalances] = useState([]); // State to hold leave balances
  const [loading, setLoading] = useState(false); // New loading state


  useEffect(() => {
        const fetchLeaveBalances = async () => {
          const response = await sendRequest('/leave-balances/me', 'POST', {userId: user.id});
          const data = response // await response.json();
          setLeaveBalances(data);
        };
    
        fetchLeaveBalances();
    }, []);

  const handleAdjust = async (e) => {
    e.preventDefault();
    setMessage('');
    setLoading(true); // Set loading to true

    if (!userId || days === 0) {
      setMessage('Please provide a valid user ID and number of days.');
      setLoading(false); // Reset loading
      return;
    }

    const response = await sendRequest('/leave-balances/admin-update', 'POST', {});
    
  };

  return (
    <div className="LMS-general-page-container">
      <Navbar />
      <div className="form-container">
        <h2>Adjust Leave Balance</h2>
        {message && <p className="message">{message}</p>}
        <form onSubmit={handleAdjust}>
          <div className="form-group">
            <label htmlFor="userId">User ID:</label>
            <input
              type="text"
              id="userId"
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="days">Days to Adjust (positive to add, negative to remove):</label>
            <input
              type="number"
              id="days"
              value={days}
              onChange={(e) => setDays(Number(e.target.value))}
              required
            />
          </div>
          <button type="submit" className="submit-button" disabled={loading}>
            {loading ? 'Adjusting...' : 'Adjust Balance'}
          </button>
        </form>
      </div>
    </div>
  );
};

export default BalanceAdjuster;