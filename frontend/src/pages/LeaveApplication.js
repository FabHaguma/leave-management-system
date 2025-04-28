import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar'; // Import Navbar
import '../styles/LeaveApplication.css';
import { AuthContext } from '../context/AuthContext'; // Import AuthContext
const { sendRequest } = require('../utils/urlBuilder');

const LeaveApplication = () => {
  const { user } = React.useContext(AuthContext); // Get user info from AuthContext
  const navigate = useNavigate(); // For navigation after submission

  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [leaveType, setLeaveType] = useState('');
  const [reason, setReason] = useState('');
  const [formError, setFormError] = useState('');
  const [leaveTypes, setLeaveTypes] = useState([]); // State to store leave types
  const [supportingDocuments, setSupportingDocuments] = useState([]);

  // Fetch leave types from the backend
  useEffect(() => {
    const fetchLeaveTypes = async () => {
      const data = await sendRequest('leave-types', 'GET', { });
      setLeaveTypes(data); // Set leave types in state
    };

    fetchLeaveTypes();
  }, []);

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

    const formData = new FormData();
    formData.append('startDate', startDate);
    formData.append('endDate', endDate);
    formData.append('leaveTypeName', leaveType);
    const selectedLeaveType = leaveTypes.find((type) => type.name === leaveType);
    formData.append('leaveTypeId', selectedLeaveType.id);
    formData.append('reason', reason);
    formData.append('userId', user.id);

    // Append files to FormData
    let documentCount = 0;
    Array.from(supportingDocuments).forEach((file) => {
      formData.append('supportingDocuments', file);
      documentCount++;
    });
    formData.append('hasDocuments', documentCount > 0);

    const response = await sendRequest('leave-requests', 'POST', formData);
    // try {
    //   const response = await fetch('http://localhost:8080/api/leave-requests', {
    //     method: 'POST',
    //     body: formData,
    //   });
  
    //   if (!response.ok) {
    //     console.error('Error submitting leave application:', response.statusText);
    //     throw new Error('Failed to submit leave application');
    //   }
  
      
    // } catch (error) {
    //   setFormError(error.message || 'Submission failed. Please try again.');
    // }
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
              {leaveTypes.map((type) => (
                <option key={type.id} value={type.name}>
                  {type.name}
                </option>
              ))}
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
              onChange={(e) => setSupportingDocuments(e.target.files)} // Handle file upload logic here
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