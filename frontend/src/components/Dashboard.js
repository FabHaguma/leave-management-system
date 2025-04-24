import React from 'react';
import { Link } from 'react-router-dom';
import Navbar from './Navbar'; // Import Navbar
import '../styles/Dashboard.css';
import { AuthContext } from '../context/AuthContext'; // Import AuthContext

function Dashboard() {
  const { user } = React.useContext(AuthContext); // Use AuthContext to get user information
  let userLevel = user ? user.level : 1; // Default to 0 if user is not available
  if (!user) {
    return <div>Loading...</div>; // Show loading state if user is not available
  }

  function checkUserAccessLevel(accessLevel, userLevel) {  
    return userLevel >= accessLevel;
  }

  const taskList = [
    { title: "Apply for Leave", access: 1, description: "Submit a new leave request.",  preview: "📝", link: "/apply-leave" },
    { title: "Leave History", access: 1, description: "View your past leave requests.",  preview: "📜", link: "/leave-history" },
    { title: "Leave Balance", access: 1, description: "View the number of days left in your yearly entitlement",  preview: "🏖️", link: "/leave-balance" },
    { title: "Application Status", access: 1, description: "Check whether your leave request has been approved",  preview: "✅", link: "/application-status" },
    { title: "Calendar", access: 1, description: "Check holidays and find colleagues on leave",  preview: "📆", link: "/calendar" },
    { title: "Manage Leave Policies", access: 3, description: "Define and update leave types and accrual rules.", preview: "📄", link: "/manage-policies" },
    { title: "Adjust Employee Balances", access: 2, description: "Manually adjust leave balances for specific employees.", preview: "📊", link: "/adjust-balances" },
    { title: "Generate Reports", access: 3, description: "View leave usage reports and statistics.", preview: "📈", link: "/generate-reports" },
    { title: "Notification Center", access: 2, description: "Manage system notifications and alerts.", preview: "🔔", link: "/notification-center" },
    { title: "Team Calendar", access: 2, description: "See team members' approved leave.", preview: "📅", link: "/team-calendar" },
    { title: "Manage Users & Roles", access: 3, description: "Add, remove, or modify user accounts and permissions.", preview: "👥", link: "/manage-users" },
    { title: "System Settings", access: 3, description: "Configure application-wide settings.", preview: "⚙️", link: "/system-settings" },
  ];

  return (
    <div className="LMS-general-page-container">
      <Navbar /> {/* Add Navbar at the top */}
      <div className="page-container admin-container">
        <div className="content-grid admin-grid">
        {taskList
          .filter(task => checkUserAccessLevel(task.access, userLevel))
          .map((task, index) => (
            <div key={index} className="card-wrapper">
              <Link to={task.link} className="card-link">
                <div className="card admin-card">
                  <div className="card-title-bar">
                    <h3>{task.title}</h3>
                  </div>
                  <div className="card-preview">
                    <span className="preview-placeholder">{task.preview || '🖼️'}</span>
                    <p className="card-description">{task.description}</p>
                  </div>
                </div>
              </Link>
              <Link to={`${task.link}/details`} className="secondary-link" />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Dashboard;