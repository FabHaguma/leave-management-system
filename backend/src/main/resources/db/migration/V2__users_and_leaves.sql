-- Seed data for Users Table
INSERT INTO users (email, name, hashpassword, profile_picture) VALUES
('john.doe@ist.com', 'John Doe', 'hashed_password_1', 'https://example.com/john.jpg'),
('jane.smith@ist.com', 'Jane Smith', 'hashed_password_2', 'https://example.com/jane.jpg'),
('admin@ist.com', 'Admin User', 'hashed_password_3', 'https://example.com/admin.jpg');

-- Seed data for User Roles Table
INSERT INTO user_roles (user_id, role_id) VALUES
((SELECT id FROM users WHERE email = 'john.doe@ist.com'), 1), -- John Doe as STAFF
((SELECT id FROM users WHERE email = 'jane.smith@ist.com'), 2), -- Jane Smith as MANAGER
((SELECT id FROM users WHERE email = 'admin@ist.com'), 3); -- Admin User as ADMIN

-- Seed data for Leave Balances Table
INSERT INTO leave_balances (user_id, leave_type_id, entitlement, used, remaining) VALUES
((SELECT id FROM users WHERE email = 'john.doe@ist.com'), (SELECT id FROM leave_types WHERE name = 'Annual Leave'), 20, 5, 15), -- John Doe's Personal Time Off
((SELECT id FROM users WHERE email = 'jane.smith@ist.com'), (SELECT id FROM leave_types WHERE name = 'Sick Leave'), 15, 3, 12); -- Jane Smith's Sick Leave

-- Seed data for Leave Requests Table
INSERT INTO leave_requests (user_id, leave_type_id, start_date, end_date, reason, status) VALUES
((SELECT id FROM users WHERE email = 'john.doe@ist.com'), (SELECT id FROM leave_types WHERE name = 'Annual Leave'), '2025-04-01', '2025-04-05', 'Family vacation', 'APPROVED'),
((SELECT id FROM users WHERE email = 'jane.smith@ist.com'), (SELECT id FROM leave_types WHERE name = 'Sick Leave'), '2025-04-10', '2025-04-12', 'Medical checkup', 'PENDING');

-- Seed data for Documents Table
INSERT INTO documents (file_name, file_url, leave_request_id) VALUES
('vacation_plan.pdf', 'https://example.com/vacation_plan.pdf', (SELECT id FROM leave_requests WHERE reason = 'Family vacation')),
('medical_report.pdf', 'https://example.com/medical_report.pdf', (SELECT id FROM leave_requests WHERE reason = 'Medical checkup'));

-- Seed data for Notifications Table
INSERT INTO notifications (recipient_id, message, type, read, timestamp) VALUES
((SELECT id FROM users WHERE email = 'john.doe@ist.com'), 'Your leave request has been approved.', 'LEAVE_APPROVED', TRUE, '2025-04-06T10:00:00Z'),
((SELECT id FROM users WHERE email = 'jane.smith@ist.com'), 'You have a pending leave request.', 'LEAVE_PENDING', FALSE, '2025-04-11T08:00:00Z');
