import React, { useState, useEffect } from 'react';
import Navbar from './Navbar';
import '../styles/ManageUsers.css';

const ManageUsers = () => {
  const [users, setUsers] = useState([]);
  const [roles, setRoles] = useState({});

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await fetch('/api/users');
        const data = await response.json();
        setUsers(data);
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    };

    fetchUsers();
  }, []);

  const handleRoleChange = (userId, newRole) => {
    setRoles((prevRoles) => ({ ...prevRoles, [userId]: newRole }));
  };

  const saveRoles = async () => {
    try {
      await Promise.all(
        Object.entries(roles).map(([userId, role]) =>
          fetch(`/api/users/${userId}/role`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ role }),
          })
        )
      );
      alert('Roles updated successfully!');
    } catch (error) {
      console.error('Error updating roles:', error);
    }
  };

  return (
    <div className="LMS-general-page-container">
      <Navbar />
      <div className="content">
        <h1>Manage Users</h1>
        <table className="users-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Role</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.name}</td>
                <td>{user.email}</td>
                <td>
                  <select
                    value={roles[user.id] || user.role}
                    onChange={(e) => handleRoleChange(user.id, e.target.value)}
                  >
                    <option value="staff">Staff</option>
                    <option value="manager">Manager</option>
                  </select>
                </td>
                <td>
                  <button onClick={() => handleRoleChange(user.id, user.role)}>
                    Reset
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <button onClick={saveRoles} className="save-button">
          Save Changes
        </button>
      </div>
    </div>
  );
};

export default ManageUsers;