import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import LoginPage from './pages/LoginPage';
import Dashboard from './pages/Dashboard';
import LeaveApplication from './pages/LeaveApplication';
import LeaveHistory from './pages/LeaveHistory';
import LeaveBalance from './pages/LeaveBalance';
import ApplicationStatus from './pages/ApplicationStatus';
import CalendarManager from './pages/CalendarManager';
import LeavePolicies from './pages/LeavePolicies';
import BalanceAdjuster from './pages/BalanceAdjuster';
import ReportGenerator from './pages/ReportGenerator';
import NotificationCenter from './pages/NotificationCenter';
import ManageUsers from './pages/ManageUsers';

const App = () => {
  return (
    <Router>
      <AuthProvider>
        <Routes>
          <Route path="/" element={<Navigate to="/login" />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/apply-leave" element={<LeaveApplication />} />
          <Route path="/leave-history" element={<LeaveHistory />} />
          <Route path="/leave-balance" element={<LeaveBalance />} />
          <Route path="/application-status" element={<ApplicationStatus />} />
          <Route path="/calendar" element={<CalendarManager />} />
          <Route path="/manage-policies" element={<LeavePolicies />} />
          <Route path="/adjust-balances" element={<BalanceAdjuster />} />
          <Route path="/generate-reports" element={<ReportGenerator />} />
          <Route path="/notification-center" element={<NotificationCenter />} />
          <Route path="/manage-users" element={<ManageUsers />} />
        </Routes>
      </AuthProvider>
    </Router>
  );
};

export default App;