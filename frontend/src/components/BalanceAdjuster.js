import React, { useState } from 'react';
import Navbar from './Navbar';
import '../styles/BalanceAdjuster.css';

const BalanceAdjuster = () => {
  const [userId, setUserId] = useState('');
  const [days, setDays] = useState(0);
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false); // New loading state

  const handleAdjust = async (e) => {
    e.preventDefault();
    setMessage('');
    setLoading(true); // Set loading to true

    if (!userId || days === 0) {
      setMessage('Please provide a valid user ID and number of days.');
      setLoading(false); // Reset loading
      return;
    }

    try {
      // Simulate API call (replace with actual API endpoint)
      const response = await fetch(`/api/admin/users/${userId}/leave-balances`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ days }),
      });

      if (response.ok) {
        setMessage('Leave balance updated successfully.');
      } else {
        setMessage('Failed to update leave balance.');
      }
    } catch (error) {
      setMessage('An error occurred. Please try again.');
    } finally {
      setLoading(false); // Reset loading
    }
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