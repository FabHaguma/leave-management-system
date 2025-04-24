import React, { useContext } from 'react';
import { AuthContext } from '../context/AuthContext'; // Import AuthContext
import '../styles/Navbar.css'; // Import CSS for styling
import defaultAvatar from '../assets/default-avatar.png'; // Import default avatar

const Navbar = () => {
  const { user, logout } = useContext(AuthContext); // Use AuthContext to get user and logout function

  const profilePicUrl = user?.profilePictureUrl || defaultAvatar; // Use default avatar if none provided

  const handleLogout = () => {
    logout();
  };

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <a href="/dashboard" className="navbar-logo">IST Africa</a>
      </div>
      <div className="navbar-title">
        <a href="/dashboard">Leave Management</a>
      </div>
      {user && (
        <div className="navbar-user-info">
          <div className="user-details">
            <img
              src={profilePicUrl}
              alt="Profile"
              className="profile-picture"
            />
            <span className="welcome-message">{user.name}</span>           
          </div>
          
          <button onClick={handleLogout} className="logout-button" title="Logout">
            {/* Using an icon or text for logout */}
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="logout-icon"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path><polyline points="16 17 21 12 16 7"></polyline><line x1="21" y1="12" x2="9" y2="12"></line></svg>
            {/* Or simply use text: Logout */}
          </button>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
