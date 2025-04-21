import React, { useState } from 'react';
import { useLeave } from '../context/LeaveContext'; // Updated from contexts to context
import { useNavigate } from 'react-router-dom'; // Updated to v7

const ApplyLeavePage = () => {
  const { applyForLeave, loading, error } = useLeave();
  const navigate = useNavigate(); // Updated from history to navigate

  // Mock leave types - fetch from backend/context later
  const leaveTypes = ['Annual', 'Sick', 'Unpaid', 'Other'];

  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [leaveType, setLeaveType] = useState(leaveTypes[0]);
  const [reason, setReason] = useState('');
  const [formError, setFormError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setFormError(''); // Clear previous errors

    if (!startDate || !endDate || !leaveType) {
      setFormError('Please fill in all required fields.');
      return;
    }
    if (new Date(endDate) < new Date(startDate)) {
        setFormError('End date cannot be before start date.');
        return;
    }

    const requestData = {
      startDate,
      endDate,
      type: leaveType,
      reason,
      // Add document upload field later
    };

    const success = await applyForLeave(requestData);

    if (success) {
      // Redirect to dashboard or history page after successful submission
      navigate('/'); // Updated from history.push
    } else {
      // Error state is handled in the context, display it
      setFormError(error || "Submission failed. Please try again.");
    }
  };

  return (
    <div>
      <h2>Apply for Leave</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="startDate">Start Date:</label>
          <input
            type="date"
            id="startDate"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
            required
            disabled={loading}
          />
        </div>
        <div>
          <label htmlFor="endDate">End Date:</label>
          <input
            type="date"
            id="endDate"
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
            required
            disabled={loading}
          />
        </div>
        <div>
          <label htmlFor="leaveType">Leave Type:</label>
          <select
            id="leaveType"
            value={leaveType}
            onChange={(e) => setLeaveType(e.target.value)}
            required
            disabled={loading}
          >
            {leaveTypes.map(type => (
              <option key={type} value={type}>{type}</option>
            ))}
          </select>
        </div>
        <div>
          <label htmlFor="reason">Reason (Optional):</label>
          <textarea
            id="reason"
            value={reason}
            onChange={(e) => setReason(e.target.value)}
            rows="3"
            disabled={loading}
          />
        </div>
        {/* Add File Upload component here later */}

        {(formError || error) && <p style={{ color: 'red' }}>{formError || error}</p>}

        <button type="submit" disabled={loading}>
          {loading ? 'Submitting...' : 'Submit Request'}
        </button>
        <button type="button" onClick={() => navigate(-1)} disabled={loading}>Cancel</button> {/* Updated to v7 */}
      </form>
    </div>
  );
};

export default ApplyLeavePage;