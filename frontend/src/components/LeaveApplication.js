import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from './Navbar'; // Import Navbar
import '../styles/LeaveApplication.css';
import { AuthContext } from '../context/AuthContext'; // Import AuthContext

const LeaveApplication = () => {
  const { user } = React.useContext(AuthContext); // Get user info from AuthContext
  const navigate = useNavigate(); // For navigation after submission

  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [leaveType, setLeaveType] = useState('');
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
      userId: user.id, // Include user ID from AuthContext
    };

    // Simulate API call (replace with actual API call)
    const success = true; // Replace with actual success response
    const error = null; // Replace with actual error response if any

    if (success) {
      navigate('/'); // Redirect to dashboard or history page
    } else {
      setFormError(error || 'Submission failed. Please try again.');
    }
  };

  return (
    <div className="LMS-general-page-container">
      <Navbar /> {/* Navbar placed above the form */}
      <div className="form-container">
        <h2>Leave Application</h2>
        {formError && <p className="error-message">{formError}</p>}
        {/* Add a note at the top of the form to explain the red star */}
        <p className="required-note">Fields marked with <span className="required-star">*</span> are required.</p>
        <br />
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="startDate">Start Date: <span className="required-star">*</span></label>
            <input
              type="date"
              id="startDate"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="endDate">End Date: <span className="required-star">*</span></label>
            <input
              type="date"
              id="endDate"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="leaveType">Leave Type: <span className="required-star">*</span></label>
            <select
              id="leaveType"
              value={leaveType}
              onChange={(e) => setLeaveType(e.target.value)}
              required
            > 
              <option value="">Select Leave Type</option>
              <option value="annual">Annual Leave</option>
              <option value="sick">Sick Leave</option>
              <option value="maternity">Maternity Leave</option>
              <option value="unpaid">Unpaid Leave</option>
              <option value="compassionate">Compassionate Leave</option>
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="reason">Reason:</label>
            <textarea
              id="reason"
              value={reason}
              onChange={(e) => setReason(e.target.value)}
              placeholder="Provide a reason for your leave (optional)"
            />
          </div>
          <div className="form-group">
            <label htmlFor="supportingDocuments">Supporting Documents:</label>
            <input
              type="file"
              id="supportingDocuments"
              onChange={(e) => console.log(e.target.files)} // Handle file upload logic here
              multiple
            />
          </div>
          <button type="submit" className="submit-button">
            Submit Application
          </button>
        </form>
      </div>
    </div>
  );
};

export default LeaveApplication;