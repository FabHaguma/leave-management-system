import React from 'react';
import { useAuth } from '../context/AuthContext';

const Navbar = () => {
  const { user } = useAuth();

  return (
    <nav style={{ padding: '1rem', backgroundColor: '#f0f0f0' }}>
      <h3>Leave Management System</h3>
      {user && (
        <div style={{ float: 'right' }}>
          <span>Welcome, {user.name}</span>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
