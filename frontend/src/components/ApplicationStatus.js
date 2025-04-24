import React, { useEffect, useState } from 'react';
import Navbar from './Navbar';
import '../styles/ApplicationStatus.css';

const ApplicationStatus = () => {
  const [application, setApplication] = useState(null);

  useEffect(() => {
    // Simulate API call to fetch recent leave application
    const fetchApplication = async () => {
      try {
        const response = {
          startdate: '2025-04-01',
          enddate: '2025-04-10',
          type: 'Annual Leave',
          status: 'Approved',
          reason: 'Family vacation',
        };
        setApplication(response);
      } catch (error) {
        console.error('Failed to fetch application status:', error);
      }
    };

    fetchApplication();
  }, []);

  return (
    <div className="LMS-general-page-container">
      <Navbar />
      <div className="application-status-content">
        <h2>Application Status</h2>
        {application ? (
          <table className="application-status-table">
            <thead>
              <tr>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Type</th>
                <th>Status</th>
                <th>Reason</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{application.startdate}</td>
                <td>{application.enddate}</td>
                <td>{application.type}</td>
                <td>{application.status}</td>
                <td>{application.reason}</td>
              </tr>
            </tbody>
          </table>
        ) : (
          <p>Loading...</p>
        )}
      </div>
    </div>
  );
};

export default ApplicationStatus;