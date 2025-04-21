import React from "react";
import { Navigate } from "react-router-dom"; // Use Navigate for v6 redirect
import { useAuth } from "../context/AuthContext"; // Corrected path

const ProtectedRoute = ({ children }) => { // Accept children prop for v6
  const { user, loading } = useAuth(); // Get loading state

  if (loading) {
    // You might want to render a proper loading spinner component here
    return <div>Loading...</div>;
  }

  return user ? children : <Navigate to="/login" />; // Use Navigate component
};

export default ProtectedRoute;