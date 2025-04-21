import React from 'react';
import { useLeave } from '../context/LeaveContext'; // Updated from contexts to context
import { Link } from 'react-router-dom';

const LeaveHistoryPage = () => {
  const { leaveRequests, loading, error } = useLeave();

  return (
    <div>
      <h2>My Leave History</h2>
      <Link to="/">Back to Dashboard</Link>
      <hr/>
      {loading && <p>Loading leave history...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {!loading && !error && (
        <>
          {leaveRequests.length === 0 ? (
            <p>You haven't applied for any leave yet.</p>
          ) : (
            <table border="1" style={{ width: '100%', borderCollapse: 'collapse' }}>
              <thead>
                <tr>
                  <th>Start Date</th>
                  <th>End Date</th>
                  <th>Type</th>
                  <th>Status</th>
                  <th>Reason</th> {/* Add reason if available */}
                </tr>
              </thead>
              <tbody>
                {leaveRequests.map(req => (
                  <tr key={req.id}>
                    <td>{req.startDate}</td>
                    <td>{req.endDate}</td>
                    <td>{req.type}</td>
                    <td>{req.status}</td>
                    <td>{req.reason || '-'}</td> {/* Display reason */}
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </>
      )}
    </div>
  );
};

export default LeaveHistoryPage;