import React, { useState } from 'react';
import Navbar from './Navbar';
import '../styles/LeavePolicies.css';

const LeavePolicies = () => {
  const [leaveTypes, setLeaveTypes] = useState([]);
  const [newLeaveType, setNewLeaveType] = useState({ leaveName: '', defaultDays: '', autoAccrual: false, carryForwardDays: '' });

  const handleAddLeaveType = () => {
    setLeaveTypes([...leaveTypes, newLeaveType]);
    setNewLeaveType({ leaveName: '', defaultDays: '', autoAccrual: false, carryForwardDays: '' });
  };

  return (
    <div className="LMS-general-page-container">
      <Navbar />
      <div className="policies-container">
        <h2>Leave Policies</h2>
        <div className="form-group">
          <label>Leave Name:</label>
          <input
            type="text"
            value={newLeaveType.leaveName}
            onChange={(e) => setNewLeaveType({ ...newLeaveType, leaveName: e.target.value })}
          />
        </div>
        <div className="form-group">
          <label>Default Days:</label>
          <input
            type="number"
            value={newLeaveType.defaultDays}
            onChange={(e) => setNewLeaveType({ ...newLeaveType, defaultDays: e.target.value })}
          />
        </div>
        <div className="form-group">
          <label>Auto Accrual:</label>
          <input
            type="checkbox"
            checked={newLeaveType.autoAccrual}
            onChange={(e) => setNewLeaveType({ ...newLeaveType, autoAccrual: e.target.checked })}
          />
        </div>
        <div className="form-group">
          <label>Carry Forward Days:</label>
          <input
            type="number"
            value={newLeaveType.carryForwardDays}
            onChange={(e) => setNewLeaveType({ ...newLeaveType, carryForwardDays: e.target.value })}
          />
        </div>
        <button onClick={handleAddLeaveType}>Add Leave Type</button>

        <h3>Existing Leave Types</h3>
        <ul>
          {leaveTypes.map((type, index) => (
            <li key={index}>{`${type.leaveName} - ${type.defaultDays} days`}</li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default LeavePolicies;