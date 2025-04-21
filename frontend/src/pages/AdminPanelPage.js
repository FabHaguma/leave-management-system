import React from 'react';
import { useAuth } from '../context/AuthContext'; // Updated from contexts to context
import { Link, Navigate } from 'react-router-dom'; // Added Navigate for conditional redirect

const AdminPanelPage = () => {
  const { user } = useAuth();

  // Ensure user has the correct role (though routing should protect this)
  if (!user || user.role !== 'Admin') {
    return <Navigate to="/" replace />; // Updated to use Navigate component
  }

  return (
    <div>
      <h2>Admin Panel</h2>
       <Link to="/">Back to Dashboard</Link>
      <hr/>
      <p>Manage system settings and user data.</p>
      <ul>
        <li><Link to="/admin/manage-policies">Manage Leave Policies</Link></li>
        <li><Link to="/admin/adjust-balances">Adjust Employee Balances</Link></li>
        <li><Link to="/admin/reports">Generate Reports</Link></li>
        <li><Link to="/admin/manage-users">Manage Users & Roles</Link></li> {/* Added user management */}
        {/* Add more admin sections as needed */}
      </ul>

      {/* You can start building out sub-components/pages for each link */}
      {/* Example: <Route path="/admin/manage-policies" component={ManagePoliciesPage} /> */}

    </div>
  );
};

export default AdminPanelPage;