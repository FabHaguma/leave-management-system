import React from 'react';
import { Link } from 'react-router-dom'; // Use Link for internal navigation
import { useAuth } from '../context/AuthContext'; // Updated from contexts to context
import { useLeave } from '../context/LeaveContext'; // Updated from contexts to context
import { useNotifications } from '../context/NotificationContext'; // Updated from contexts to context

const EmployeeDashboardPage = () => {
  const { user, logout } = useAuth();
  const { leaveBalance, leaveRequests, loading: leaveLoading, error: leaveError, refetchLeaves } = useLeave();
  const { notifications, unreadCount, loading: notifLoading, error: notifError, refetchNotifications } = useNotifications();

  const isLoading = leaveLoading || notifLoading;

  const handleRefresh = () => {
    refetchLeaves();
    refetchNotifications();
  }

  return (
    <div>
      <h1>Welcome, {user?.name}!</h1>
      <p>Role: {user?.role}</p>
      <button onClick={logout}>Logout</button>
      <button onClick={handleRefresh} disabled={isLoading}>
        {isLoading ? 'Refreshing...' : 'Refresh Data'}
      </button>
      <hr />

      <div>
        <Link to="/apply-leave">Apply for Leave</Link> |{' '}
        <Link to="/leave-history">View Leave History</Link>
        {/* Add links based on role */}
        {(user?.role === 'Manager' || user?.role === 'Admin') && (
          <>
            {' '} | <Link to="/approvals">Manage Approvals</Link>
          </>
        )}
         {user?.role === 'Admin' && (
           <>
             {' '} | <Link to="/admin">Admin Panel</Link>
           </>
         )}
         {(user?.role === 'Manager' || user?.role === 'Admin' || user?.role === 'Staff') && ( // Assuming everyone can see team calendar
           <>
             {' '} | <Link to="/team-calendar">Team Calendar</Link>
           </>
         )}
      </div>
      <hr />


      <h2>Notifications ({unreadCount} unread)</h2>
      {isLoading && <p>Loading notifications...</p>}
      {notifError && <p style={{ color: 'red' }}>{notifError}</p>}
      {!isLoading && !notifError && (
        <ul>
          {notifications.length === 0 && <li>No notifications.</li>}
          {notifications.slice(0, 5).map(n => (
            <li key={n.id} style={{ fontWeight: n.read ? 'normal' : 'bold' }}>
              {n.message} - <small>{new Date(n.timestamp).toLocaleString()}</small>
              {/* Add mark as read button later */}
            </li>
          ))}
        </ul>
      )}


      <h2>Leave Balance</h2>
      {isLoading && <p>Loading balance...</p>}
      {leaveError && <p style={{ color: 'red' }}>{leaveError}</p>}
      {!isLoading && !leaveError && leaveBalance ? (
        <ul>
          <li>Annual Leave: {leaveBalance.annual ?? 'N/A'} days</li>
          <li>Sick Leave: {leaveBalance.sick ?? 'N/A'} days</li>
          <li>Unpaid Leave: {leaveBalance.unpaid ?? 'N/A'} days</li>
          {/* Add other types as needed */}
        </ul>
      ) : (
        !isLoading && !leaveError && <p>Leave balance not available.</p>
      )}

      <h2>Recent Leave Requests</h2>
      {isLoading && <p>Loading requests...</p>}
      {leaveError && <p style={{ color: 'red' }}>{leaveError}</p>}
      {!isLoading && !leaveError && (
         <ul>
           {leaveRequests.length === 0 && <li>No recent leave requests found.</li>}
           {leaveRequests.slice(0, 5).map(req => (
             <li key={req.id}>
               {req.type}: {req.startDate} to {req.endDate} - Status: {req.status}
             </li>
           ))}
         </ul>
      )}
       {leaveRequests.length > 5 && <Link to="/leave-history">View all...</Link>}

    </div>
  );
};

export default EmployeeDashboardPage;