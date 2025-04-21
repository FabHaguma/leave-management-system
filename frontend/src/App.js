import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { LeaveProvider } from './context/LeaveContext'; // Import LeaveProvider
import { NotificationProvider } from './context/NotificationContext'; // Import NotificationProvider
import ProtectedRoute from './routes/ProtectedRoute';
import LoginPage from './pages/LoginPage';
// Import the page components
import EmployeeDashboardPage from './pages/EmployeeDashboardPage';
import ApplyLeavePage from './pages/ApplyLeavePage';
import LeaveHistoryPage from './pages/LeaveHistoryPage';
import ApprovalPage from './pages/ApprovalPage';
import AdminPanelPage from './pages/AdminPanelPage';
import TeamCalendarPage from './pages/TeamCalendarPage';
import Navbar from './components/Navbar';

const App = () => {
  return (
    <AuthProvider>
      <LeaveProvider>
        <NotificationProvider>
          <Router>
            <Navbar />
            <div style={{ padding: '20px' }}>
              <Routes>
                <Route path="/login" element={<LoginPage />} />
                
                {/* Protected Routes */}
                <Route path="/" element={<ProtectedRoute><EmployeeDashboardPage /></ProtectedRoute>} />
                <Route path="/apply-leave" element={<ProtectedRoute><ApplyLeavePage /></ProtectedRoute>} />
                <Route path="/leave-history" element={<ProtectedRoute><LeaveHistoryPage /></ProtectedRoute>} />
                <Route path="/approvals" element={<ProtectedRoute><ApprovalPage /></ProtectedRoute>} />
                <Route path="/admin" element={<ProtectedRoute><AdminPanelPage /></ProtectedRoute>} />
                <Route path="/team-calendar" element={<ProtectedRoute><TeamCalendarPage /></ProtectedRoute>} />
                
                {/* Fallback for unknown routes */}
                <Route path="*" element={<Navigate replace to="/" />} />
              </Routes>
            </div>
          </Router>
        </NotificationProvider>
      </LeaveProvider>
    </AuthProvider>
  );
};

export default App;
