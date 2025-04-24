import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import LoginPage from './components/LoginPage';
import Dashboard from './components/Dashboard';
import LeaveApplication from './components/LeaveApplication';
import LeaveHistory from './components/LeaveHistory';
import LeaveBalance from './components/LeaveBalance';
import ApplicationStatus from './components/ApplicationStatus';
import CalendarManager from './components/CalendarManager';
import LeavePolicies from './components/LeavePolicies';
import BalanceAdjuster from './components/BalanceAdjuster';
import ReportGenerator from './components/ReportGenerator';
import NotificationCenter from './components/NotificationCenter';
import ManageUsers from './components/ManageUsers';

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