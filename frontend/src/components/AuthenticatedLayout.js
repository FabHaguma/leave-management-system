import React from 'react';
import Navbar from './Navbar';

const AuthenticatedLayout = ({ children }) => {
  return (
    <div>
      <Navbar />
      <div style={{ padding: '20px' }}>{children}</div>
    </div>
  );
};

export default AuthenticatedLayout;