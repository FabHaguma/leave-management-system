import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import '../styles/ApplicationStatus.css';
import { AuthContext } from '../context/AuthContext'; // Import AuthContext
const { sendRequest } = require('../utils/urlBuilder');

const ApplicationStatus = () => {
  const [application, setApplication] = useState(null);
  const { user } = React.useContext(AuthContext); // Get user info from AuthContext
  useEffect(() => {
    const fetchLeaveApplication = async () => {

      const data = await sendRequest('leave-requests/leave-status', 'GET', {userId: user.id});
      setApplication(data);
      
    };

    fetchLeaveApplication();
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
                <th>Type</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{application.type}</td>
                <td>{application.startdate}</td>
                <td>{application.enddate}</td>
                <td>{application.status}</td>
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