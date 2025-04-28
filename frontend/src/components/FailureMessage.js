import React from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from './Navbar';

const FailureMessage = ({ message }) => {
  const navigate = useNavigate();

  return (
    <div className="LMS-general-page-container">
      <Navbar />
      <div className="message-container">
        <h2>Something went wrong!</h2>
        <p>{message}</p>
        <button onClick={() => navigate('/dashboard')}>Go to Dashboard</button>
        <button onClick={() => navigate(-1)}>Go Back</button>
      </div>
    </div>
  );
};

export default FailureMessage;