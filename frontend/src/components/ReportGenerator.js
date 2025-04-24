import React, { useState } from 'react';
import Navbar from './Navbar';
import '../styles/ReportGenerator.css';

const ReportGenerator = () => {
  const [reports, setReports] = useState([]);
  const [sortField, setSortField] = useState('');

  const handleGenerateReport = () => {
    // Placeholder for generating reports
    const generatedReports = [
      { leavetype: 'Sick Leave', defaultdays: 10, startDate: '2025-01-01', endDate: '2025-01-10', employeename: 'John Doe' },
      { leavetype: 'Annual Leave', defaultdays: 20, startDate: '2025-02-01', endDate: '2025-02-20', employeename: 'Jane Smith' },
    ];
    setReports(generatedReports);
  };

  const handleSort = (field) => {
    setSortField(field);
    const sortedReports = [...reports].sort((a, b) => (a[field] > b[field] ? 1 : -1));
    setReports(sortedReports);
  };

  return (
    <div className="LMS-general-page-container">
      <Navbar />
      <div className="content">
        <h1>Leave Usage Reports</h1>
        <table>
          <thead>
            <tr>
              <th onClick={() => handleSort('leavetype')}>Leave Type</th>
              <th onClick={() => handleSort('defaultdays')}>Default Days</th>
              <th onClick={() => handleSort('startDate')}>Start Date</th>
              <th onClick={() => handleSort('endDate')}>End Date</th>
              <th onClick={() => handleSort('employeename')}>Employee Name</th>
            </tr>
          </thead>
          <tbody>
            {reports.map((report, index) => (
              <tr key={index}>
                <td>{report.leavetype}</td>
                <td>{report.defaultdays}</td>
                <td>{report.startDate}</td>
                <td>{report.endDate}</td>
                <td>{report.employeename}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <button onClick={handleGenerateReport}>Generate Report</button>
      </div>
    </div>
  );
};

export default ReportGenerator;